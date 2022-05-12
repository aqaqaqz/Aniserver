<template>
  <div class="album py-5 bg-light">
    <div class="container">

      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3"  v-for="(recent, index) in pageData.recentList" v-bind:key="index">
        <div class="col">
          <div class="card shadow-sm">
            <!-- <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#55595c"></rect><text x="50%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text></svg> -->
            <img height="225" src="https://mblogthumb-phinf.pstatic.net/20130526_74/vozel_1369554223132PY16n_JPEG/%C4%AB%C1%EE%BB%E741.jpg?type=w2">
            <div class="card-body">
              <p class="card-text">{{recent.name}}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { defineComponent, reactive, onMounted } from "vue";

export default defineComponent({
  name : "Main",
  data(){
    let pageData = reactive({
      recentList : []
    });

    let initFunc = getInitFuncs({pageData});

    onMounted( async ()=>{
      await initFunc.initRecentList();
    });

    return {pageData};
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

<style scoped></style>
