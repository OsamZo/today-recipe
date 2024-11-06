<template>
  <div class="page-content">
    <div class="shop-card">
      <img src="https://goruna.s3.us-west-1.amazonaws.com/94ed28ca-02d8-4793-9b08-765e355b0fd2_%EB%A3%A8%ED%94%BC.jpg" alt="가게 사진" class="shop-image" />
      <p class="shop-title">쿠키집</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ShopCard'
};
</script>

<style scoped>
.page-content {
  width: 80vw;
  height: auto; 
  background-color: #ffffff;
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  padding-top: 5vh;
  padding-left: 2vw; 
  margin: 0 auto;
  margin-top: 20vh;
  box-sizing: border-box;
  position: relative;
}

.shop-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 150px;
  padding: 10px;
  top: -100px;
  left: 50px;
  position: relative;
  align-items: flex-start;
  z-index: 1;
}

.shop-image {
  width: 100%;
  height: auto;
  border-radius: 8px;
  margin-bottom: 10px;
}

.shop-title {
  font-size: 18px;
  font-weight: bold;
  text-align: left;
}
</style>
