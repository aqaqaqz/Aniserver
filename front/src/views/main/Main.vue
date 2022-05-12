<template>
  <div class="album py-5 bg-light">
    <div class="container">

      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <div class="col" v-for="(recent, index) in pageData.recentList" v-bind:key="index" @click="goToPlayer(encodeURIComponent(recent.path))">
          <div class="card shadow-sm">
            <img height="225" :src="'http://localhost:8888/api/directory/thumbnail?name='+encodeURIComponent(recent.name)">
            <div class="card-body">
              <p class="card-text ellipsis">{{recent.name}}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { useRouter } from 'vue-router'
import { defineComponent, reactive, onMounted } from "vue";

export default defineComponent({
  name : "Main",
  data(){
    const router = useRouter();

    let pageData = reactive({
      recentList : []
    });

    let initFunc = getInitFuncs({pageData});

    onMounted( async ()=>{
      await initFunc.initRecentList();
    });

    return {
      pageData,
      goToPlayer : function(path){
        router.push({path: '/player/'+path, replace: true});
      }
    };
  }
})

function getInitFuncs({pageData}){ 
  return {
    initRecentList : async function(){
      await axios.get('http://localhost:8888/api/directory/recent').then( res => {
        pageData.recentList = res.data;
      })
    }
  }
};
</script>

<style scoped>
.card .ellipsis{
  overflow: hidden;
	text-overflow: ellipsis;
	word-wrap: break-word;
	display: -webkit-box;
	-webkit-line-clamp: 2; /* ellipsis line */
	-webkit-box-orient: vertical;
}
</style>
