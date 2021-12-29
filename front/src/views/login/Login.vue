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
        console.log("login...");
      })
    }
  }
}
</script>

<style scoped></style>
