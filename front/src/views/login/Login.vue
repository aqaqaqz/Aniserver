<template>
 <main>

  <section class="py-5 text-center container">
    <div class="row py-lg-5">
      <div class="col-lg-3 col-md-8 mx-auto">
        <h1 class="fw-light">Login</h1>
        <br/><br/>
        <div class="form-floating">
          <input type="text" class="form-control" id="loginId" placeholder="Text">
          <label for="loginId">id</label>
        </div>
        <div class="form-floating">
          <input type="email" class="form-control" id="loginPasswd" placeholder="Password">
          <label for="loginPasswd">passwd</label>
        </div>

        <br/>

        <button class="w-100 btn btn-lg btn-secondary" type="submit" id="loginBtn">Login</button>
      </div>
    </div>
  </section>
</main>
</template>

<script>
import { defineComponent, onMounted } from "vue";
import axios from 'axios';

export default defineComponent({
  name : "Login",
  data(){
    onMounted(()=>{
      let init = getInitFuncs();
      init.initLoginBtn();
    });

    return {};
  }
})

function getInitFuncs(){
  return {
    initLoginBtn : function(){
      const loginBtn = document.getElementById("loginBtn");
      loginBtn.addEventListener('click', (e)=>{
        let id = document.getElementById("loginId").value;
        let passwd = document.getElementById("loginPasswd").value;

        if(id==null || id==""){
          alert("아이디를 입력해주세요");
          return;
        }
        if(passwd==null || passwd==""){
          alert("비밀번호를 입력해주세요");
          return;
        }

        let param = {
          id : id,
          passwd : passwd
        };

        axios.post('http://localhost:8888/api/user/login', param).then( res => {
          location.href = '/';

        })
      })
    }
  }
}
</script>

<style scoped></style>
