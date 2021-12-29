<template>
  <header class="p-3 bg-dark text-white">
    <div class="container">
      <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
        <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
          <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"></use></svg>
        </a>

        <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
          <li><a href="#" class="nav-link px-2 text-secondary">Home</a></li>

          <li v-for="year in pageData.folderList" v-bind:key="year.name">
            <div class="dropdown">
              <button class="btn btn-dark dropdown-toggle" type="button" id="dropdownMenuButton2" data-bs-toggle="dropdown" aria-expanded="false">
                {{year.name}}
              </button>
              <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="dropdownMenuButton2">
                <li v-for="quarter in year.subList" v-bind:key="quarter.name">
                  <a class="dropdown-item" href="#">{{quarter.name}}</a>
                </li>
              </ul>
            </div>
          </li>

          <li><a href="#" class="nav-link px-2 text-white">ETC</a></li>
          <li><a href="#" class="nav-link px-2 text-white">TEMP</a></li>
          <li><a href="#" class="nav-link px-2 text-white">DOWN</a></li>
        </ul>

        <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
          <input type="search" class="form-control form-control-dark" placeholder="Search" aria-label="Search">
        </form>

        <div class="text-end">
          <button type="button" class="btn btn-outline-light me-2" id="loginBtn">Login</button>
          <button type="button" class="btn btn-warning">Sign-up</button>
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

    axios.get('http://localhost:8080/api/directory').then(res => {
      for(let idx in res.data){
        let f = res.data[idx];
        let folder = pageData.folderList.find(folder => folder.name == f.name.substr(0, 4));
        if(!folder) pageData.folderList.push({name:f.name.substr(0, 4), subList : []}); 
        
        pageData.folderList.find(folder => folder.name == f.name.substr(0, 4)).subList.push(f);
      }
    })

    onMounted(()=>{
      let init = getInitFuncs(pageData);
      init.initLoginBtn();
    })

    return {
      pageData
    };
  }
})

function getInitFuncs({router}){
  return {
    initLoginBtn : function(){
      console.log(router)

      const loginBtn = document.getElementById("loginBtn");
      loginBtn.addEventListener('click', (e)=>{
        router.push('Login');
      })
    }
  }
}
</script>

<style scoped></style>
