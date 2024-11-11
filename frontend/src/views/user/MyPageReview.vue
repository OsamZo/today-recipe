<script setup>
import { ref, onMounted } from "vue";
import MyPageBox from "@/components/MyPageBox.vue";
import { getUserReviews } from "@/api/user/GetUserReviewApi.js";
import axios from "axios"; // axios 임포트 추가

const reviews = ref([]); // 조회된 리뷰 데이터를 저장할 배열

// 로컬스토리지에서 userSeq 가져오기
const userSeq = localStorage.getItem("userSeq");

// 리뷰 데이터를 가져오는 함수
const fetchUserReviews = async () => {
  try {
    const response = await getUserReviews(userSeq);
    console.log(response.data);
    reviews.value = response.data.filter(review => review.reviewStatus && review.reviewStatus === 'Y');
  } catch (error) {
    console.error("Error fetching reviews:", error.response || error);
  }
};

const deleteReview = async (reviewSeq) => {
  try {
    const response = await axios.delete(`http://localhost:8100/api/v1/admin/review/${reviewSeq}`);
    if (response.status === 200) {
      alert("성공적으로 삭제되었습니다.");
      // 삭제 후 리뷰 목록 갱신
      fetchUserReviews();
    }
  } catch (error) {
    alert("리뷰 삭제를 실패하였습니다.");
    console.error("Error deleting review:", error.response || error);
  }
};

onMounted(fetchUserReviews);
</script>


<template>
  <MyPageBox>
    <template v-slot:action>
      <div class="container">
        <div class="header">
          <div class="title">나의 리뷰 조회</div>
        </div>

        <div class="body">
          <!-- 리뷰 목록 표시 -->
          <div v-if="reviews && reviews.length">
            <div v-for="(review, index) in reviews" :key="index" class="review-item">
              <div class="review-header">
                <div class="left">
                  <p class="name">{{ review.userNickname }} 님</p>
                  <p class="review-date">{{ new Date(review.regDate).toLocaleDateString()}}</p>
                </div>
                <div class="right">
                  <button @click="deleteReview(review.reviewSeq)" class="delete-button">삭제</button>
                </div>
              </div>
              <div class="review-content">
                <p>{{ review.reviewContent }}</p>
                <p class="like-count">Likes: {{ review.likeCount }}</p>
              </div>
              <div class="bottom-bar"/>
            </div>
          </div>
          <p v-else>조회된 리뷰가 없습니다.</p>
        </div>
      </div>
    </template>
  </MyPageBox>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
}

.title {
  min-width: 850px;
  padding: 0 0 20px 0;
  border-bottom: 1px solid #8B4513;
  font-size: 30px;
}

.body {
  padding: 20px;
}

.review-item {
  margin-bottom: 20px;
}
.left{
  display: flex;
  gap : 20px;
}
.review-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0 0 20px 0;
}

.name{
  font-weight: bold;
  font-size:20px;
}

.review-date {
  font-size: 14px;
  color: gray;
  margin: 8px 0 0 0;
}

.review-content {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin: 10px 0 10px 0;
}

.bottom-bar{
  width: 100%;
  height: 1px;
  border: 1px solid #D9D9D9;
}

.like-count {
  font-size: 14px;
  color: #8B4513;
  margin-left: 20px;
  white-space: nowrap;
}

.delete-button {
  background: none;
  border: none;
  color: #999999;
  font-size: 14px;
  cursor: pointer;
  text-decoration: underline;
}
</style>