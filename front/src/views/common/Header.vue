<template>
  <header class="p-3 bg-dark text-white">
    <div class="container">
      <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
        <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
          <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"></use></svg>
        </a>

        <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0" id="gnbMenu">
          <li><a href="/" class="nav-link px-2 text-secondary">Home</a></li>

          <li v-for="folder in pageData.folderList" v-bind:key="folder.name">
            <div class="dropdown" v-if="folder.lower.length > 0">
              <button class="btn btn-dark dropdown-toggle" type="button" :id="folder.name" data-bs-toggle="dropdown" aria-expanded="false">
                {{folder.name}}
              </button>
              <ul class="dropdown-menu dropdown-menu-dark" :aria-labelledby="folder.name">
                <li v-for="quarter in folder.lower" v-bind:key="quarter.name">
                  <a class="dropdown-item path-link" href="javascript:void(0);">{{quarter.name}}</a>
                </li>
              </ul>
            </div>

            <a href="javascript:void(0);" class="nav-link px-2 text-white path-link" v-if="folder.lower.length == 0">
              {{folder.name}}
            </a>
          </li>
        </ul>

        <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
          <input type="search" class="form-control form-control-dark" placeholder="Search" aria-label="Search">
        </form>

        <div class="text-end">
          <button type="button" class="btn btn-outline-light me-2" id="loginPageBtn">Login</button>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import axios from 'axios';
import { defineComponent, reactive, onMounted} from "vue";
import { useRouter } from 'vue-router'

import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"

export default defineComponent({
  name : "Header",
  data(){
    const pageData = reactive({
      router : useRouter(),
      folderList : []
    })

    let init = getInitFuncs(pageData);
    onMounted(async ()=>{
      init.initLoginPageBtn();
      await init.initGnbMenu();
    });

    return {
      pageData
    };
  }
})

function getInitFuncs({router, folderList}){
  let initClickGnbMenu = async function(){
    for(let li of document.querySelectorAll("#gnbMenu a.path-link")){
      let path = li.textContent;
      if(path==null || path=="") continue;

      li.addEventListener("click", (e)=>{
        router.push({name:'List', params:{path:'/'+path}});
      });

    }
  }

  return {
    initGnbMenu : async function (){
      await axios.get('http://localhost:8888/api/directory/path').then(async res => {
/*
        folderList = [{
          'yyyy':{
            subList : []
          }
        }]
*/
        let folderObject = {};
        const quarterPattern = /\d{4}-\d{1}/;
        for(let folder of res.data.lower){
          if(quarterPattern.exec(folder.name)){
            let year = folder.name.substring(0, 4);
            if(!folderObject[year]) folderObject[year] = [];
            folderObject[year].push({name : folder.name});
          }else{
            folderObject[folder.name] = [];
          }
        }

        for(let key in folderObject)
          folderList.push({
            name : key,
            lower : folderObject[key]
          });
      });

      await initClickGnbMenu();
    },
    initLoginPageBtn : function(){
      const loginBtn = document.getElementById("loginPageBtn");
      loginBtn.addEventListener('click', (e)=>{
        router.push({path: '/login', replace: true});
      })
    }
  }
}
</script>

<style scoped></style>
