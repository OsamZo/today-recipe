<script>
import axios from 'axios';

export default {
  name: 'ReviewPage',
  data() {
    return {
      restaurant: null,
      reviews: []
    };
  },
  mounted() {
    this.fetchReviews();
  },
  methods: {
    async fetchReviews() {
      const shopSeq = this.$route.params.shopSeq;
      try {
        const response = await axios.get(`http://localhost:8100/api/v1/admin/${shopSeq}/review`);
        if (response.data.success) {
          const data = response.data.data;

          if (data.length > 0) {
            // 가게 정보 가져오기
            this.restaurant = {
              name: data[0].shopName,
              image: data[0].shopImgUrl
            };

            // 리뷰 리스트 설정 (리뷰 상태가 "Y"인 경우만 표시)
            this.reviews = data
                .filter(review => review.reviewStatus === 'Y')
                .map(review => ({
                  reviewSeq: review.reviewSeq,
                  userNickname: review.userNickname,
                  reviewContent: review.reviewContent
                }));
          }
        } else {
          console.error("리뷰 데이터를 불러오는 데 실패했습니다:", response.data.message);
        }
      } catch (error) {
        console.error("리뷰 데이터를 불러오는 중 오류가 발생했습니다:", error);
      }
    },
    // async 추가
    async deleteReview(index) {
      const reviewSeq = this.reviews[index].reviewSeq;
      try {
        const response = await axios.delete(`http://localhost:8100/api/v1/admin/review/${reviewSeq}`);
        if (response.data.success) {
          this.reviews.splice(index, 1);
          alert("삭제가 완료되었습니다!");
        } else {
          console.error("리뷰 삭제에 실패했습니다.", response.data.message);
        }
      } catch (error) {
        console.error("리뷰 삭제 중 오류가 발생했습니다:", error);
      }
    }
  }
};
</script>

<template>
  <div>
    <!-- 리뷰 컨텐츠 -->
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
/* 기본 레이아웃 */
body {
  font-family: Arial, sans-serif;
  background-color: #f6f4f2;
  margin: 0;
}

.header h1 {
  font-size: 18px;
}

/* 컨텐츠 영역 */
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
