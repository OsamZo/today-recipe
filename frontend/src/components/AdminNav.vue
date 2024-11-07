<script setup>

</script>

<template>
  <div class="navBar">
    <div>
      <router-link to="/admin/shopAuth" class="auth">
        사업자 인증 조회
      </router-link>
      <br><br>
      <router-link to="/admin/shopList" class="review">
        리뷰 관리
      </router-link>
      <br><br>
      <router-link to="/admin/shopCategory" class="category">
        카테고리 관리
      </router-link>
    </div>
  </div>

</template>

<style scoped>
  .navBar{
    display: flex;
    flex-direction: column;
  }
  .auth, .category, .review{
    font-size : 28px;
    margin: 0 0 10px 0;
    text-decoration: none;
    color: black;
  }

</style>