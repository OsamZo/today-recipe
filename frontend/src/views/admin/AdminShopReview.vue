<script setup>
import { onMounted } from "vue";
import { ShopReviewApi } from "@/api/admin/ShopReviewApi.js";

const { restaurant, reviews, fetchReviews, deleteReview } = ShopReviewApi();

onMounted(() => {
  fetchReviews();
});
</script>

<template>
  <div>
    <div class="content">
      <div class="review-container">
        <!-- restaurant이 있을 때만 보여줌 -->
        <div class="restaurant-info" v-if="restaurant">
          <img :src="restaurant.image" alt="Restaurant Image"/>
          <div class="name">{{ restaurant.name }}</div>
          <div class="topBar"/>
        </div>
        <div class="review-list">
          <div class="review-item" v-for="(review, index) in reviews" :key="review.reviewSeq">
            <div class="review-contents-box">
              <div class="reviewer">{{ review.userNickname }} 님</div>
              <div class="review-text">{{ review.reviewContent }}</div>
            </div>
            <div class="delete-btn" @click="deleteReview(index)">삭제</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>
body {
  font-family: Arial, sans-serif;
  background-color: #f6f4f2;
  margin: 0;
}

.header h1 {
  font-size: 18px;
}

.content {
  display: flex;
  justify-content: center;
  padding: 20px;
}

.review-container {
  background: linear-gradient(to bottom, transparent 100px, white 100px);
  padding: 20px 116px 0 116px;
  border-radius: 8px;
  width: 60%;
  height: 120%;
  margin: 90px 0 0 0;
}

.restaurant-info {
  text-align: center;
  display: flex;
  justify-content: left;
  flex-direction: column;
}

.restaurant-info img {
  width: 180px;
  height: 180px;
  border-radius: 8px;
  margin: 0 0 20px 10px;
}

.topBar {
  height: 4px;
  border-radius: 10px;
  background: #633A02;
}

.review-contents-box {
  margin: 0 0 0 20px;
}

.name {
  font-size: 40px;
  width: 200px;
  height: 50px;
  margin: 0 0 35px 0;
}

.review-list {
  margin: 8px 0 50px 0;
}

.review-item {
  padding: 15px 0;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.review-item {
  border-bottom: 1px solid #b48b65;
}

.reviewer {
  font-weight: bold;
  font-size: 20px;
  margin: 0 0 20px 0;
}

.review-text {
  color: #555;
  margin: 5px 0;
  white-space: pre-line;
}

.delete-btn {
  color: #7f5524;
  cursor: pointer;
  font-size: 15px;
  margin: 5px 10px 0 0;
}
</style>
