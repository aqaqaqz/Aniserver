<template>
  <div class="album py-5 bg-light">
    <div class="container">
      <div class="row row-cols-1 row-cols-sm-1 row-cols-md-1 g-1">
        <div class="col">
          <div class="card shadow-sm">
            <video class="bd-placeholder-img card-img-top" focusable="false" controls="" autoplay="" name="media">
              <source :src="'http://localhost:8888/api/directory/streaming?path='+encodeURIComponent($route.params.path)" type="video/mp4">
            </video>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
//content 접근시 로그인체크로직 추가 필요함.

import { defineComponent } from "vue";

export default defineComponent({
  name : "Player",
  data(){
    return {};
  }
})
</script>

<style scoped></style>
