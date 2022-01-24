<template>
  <div class="album py-5 bg-light">
    <div class="container">
      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-1 g-1">
        <div class="col">
          <div class="card shadow-sm">
            <video class="bd-placeholder-img card-img-top" width="100%" height="100%" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false">
              <!-- 경로 api 서버에서 파일서버로 붙게 잡아줘야함. 현재 로컬테스트 중이라 접근 제대로 못함. -->
              <source :src="$route.params.path" type="video/mp4">
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
