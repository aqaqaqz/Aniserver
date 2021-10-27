<template>
  <Header></Header>
  <SideMenu></SideMenu>
  <router-view />
  <Footer></Footer>
</template>

<script>
import { defineComponent } from "vue";

import Footer  from "@/views/common/Footer";
import SideMenu  from "@/views/common/SideMenu";
import Header  from "@/views/common/Header";

export default defineComponent({
  name : "App",
  components:{
    Header,
    Footer,
    SideMenu
  },
  data(){
    return {};
  }
})
</script>

<style scoped></style>
