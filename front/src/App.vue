<template>
  <Header></Header>
  <router-view :key="$route.fullPath" />
  <Footer></Footer>
</template>

<script>
import { defineComponent } from "vue";

import Footer  from "@/views/common/Footer";
import Header  from "@/views/common/Header";

export default defineComponent({
  name : "App",
  components:{
    Header,
    Footer
  },
  data(){
    return {};
  }
})
</script>

<style scoped></style>
