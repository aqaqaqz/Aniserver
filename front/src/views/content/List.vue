<template>
  <div class="my-3 p-3 bg-body rounded shadow-sm">
    <h6 class="border-bottom pb-2 mb-0">{{$route.params.path}}</h6>

    <div class="d-flex text-muted pt-3" v-for="(info, index) in pageData.pageList" v-bind:key="index">
      <div class="pb-3 mb-0 small lh-sm border-bottom w-100 pageLinkDiv" :path="info.path" :type="info.type">
        <div class="d-flex justify-content-between">
          <strong class="text-gray-dark">{{info.name}}</strong>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
//content 접근시 로그인체크로직 추가 필요함.

import axios from 'axios';
import { useRouter } from 'vue-router'
import { defineComponent, reactive, onMounted } from "vue";

export default defineComponent({
  name : "List",
  data(){
    const router = useRouter();

    let pageData = reactive({
      pageList : []
    })

    let initFunc = getInitFuncs({pageData});
    

    onMounted( async ()=>{
      await initFunc.initPageList(this.$route.params.path);

      document.querySelectorAll("div.pageLinkDiv").forEach(el => {
        el.addEventListener('click', function(e){
          let path = this.getAttribute("path");
          let type = this.getAttribute("type");

          if(type == "F") router.push({name:'List', params:{path:path}});
          else router.push({name:'Player', params:{path:path}});
        });
      })
     
    })

    return {
      pageData
    };
  }
})

function getInitFuncs({pageData}){
  return {
    initPageList : async function(path){
      await axios.get('http://localhost:8888/api/directory/path?path='+path).then( res => {
        pageData.pageList = res.data.lower;
      })
    }
  }
};
</script>

<style scoped></style>
