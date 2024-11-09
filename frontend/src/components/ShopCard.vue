<template>
  <div class="shop-card">
      <img :src="shopImgUrl" alt="가게 사진" class="shop-image" />
      <div class="title-container">
        <p class="shop-title">{{ shopName }}</p>
        <slot name="action"></slot> <!-- 액션 슬롯 -->
        <slot name="bookmark"></slot>
      </div>
  </div>
</template>

<script setup>
defineProps({
  shopImgUrl: {
    type: String,
    required: true
  },
  shopName: {
    type: String,
    required: true
  }
});
</script>

<style scoped>
.shop-card {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  width: auto; 
  top: -100px;
  position: absolute;
  z-index: 1;
}

.shop-image {
  width: 200px; 
  height: 200px;
  border-radius: 8px;
  margin-bottom: 10px;
}

.title-container {
  display: flex;
  align-items: center; 
  justify-content: space-between;
  flex-wrap: nowrap; 
  width: 100%; 
  margin-top: 10px;
}

.shop-title {
  font-size: 40px; 
  text-align: left;
  white-space: nowrap; 
  margin-right: 10px;
}

.review-button {
  font-size: 40px;
  background-color: #8B4513;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 5px 10px;
  cursor: pointer;
}

.review-button:hover {
  background-color: #a0522d; 
}

.bookmark {
  width: 37px;
  height: 37px;
}
</style>
