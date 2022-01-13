<template>
  <div class="my-3 p-3 bg-body rounded shadow-sm">
    <h6 class="border-bottom pb-2 mb-0">{{$route.params.path}}</h6>

    <div class="d-flex text-muted pt-3" v-for="(info, index) in pageData.pageList" v-bind:key="index">
      <div class="pb-3 mb-0 small lh-sm border-bottom w-100">
        <div class="d-flex justify-content-between">
          <strong class="text-gray-dark">{{info.name}}</strong>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { defineComponent, reactive } from "vue";

export default defineComponent({
  name : "List",
  data(){

    let pageData = reactive({
      pageList : []
    })

    let initFunc = getInitFuncs({pageData});
    initFunc.initPageList(this.$route.params.path);

    return {
      pageData
    };
  }
})

function getInitFuncs({pageData}){
  return {
    initPageList : function(path){
      axios.get('http://localhost:8080/api/directory?path='+path).then( res => {
        pageData.pageList = res.data.sublist;
        console.log(pageData.pageList)
      })
    }
  }
};
</script>

<style scoped></style>
