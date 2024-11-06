<template>
  <div class="shop-card">
      <img src="https://goruna.s3.us-west-1.amazonaws.com/94ed28ca-02d8-4793-9b08-765e355b0fd2_%EB%A3%A8%ED%94%BC.jpg" alt="가게 사진" class="shop-image" />
      <div class="title-container">
        <p class="shop-title">쿠키집</p>
        <slot name="action"></slot> <!-- 액션 슬롯 -->
      </div>
  </div>
</template>

<script>
export default {
  name: 'ShopCard'
};
</script>

<style scoped>
.shop-card {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  width: auto; /* 너비 제한 해제 */
  top: -100px;
  position: absolute;
  z-index: 1;
}

.shop-image {
  width: 200px; /* 이미지의 고정 크기 */
  height: auto;
  border-radius: 8px;
  margin-bottom: 10px;
}

.title-container {
  display: flex;
  align-items: center; /* 가로로 정렬 */
  justify-content: space-between;
  flex-wrap: nowrap; /* 줄바꿈 방지 */
  width: 100%; /* 컨테이너 전체 너비 */
  margin-top: 10px;
}

.shop-title {
  font-size: 40px; /* 가게 이름 크기 */
  text-align: left;
  white-space: nowrap; /* 줄바꿈 방지 */
  margin-right: 10px; /* 버튼과 간격 조정 */
}

.review-button {
  font-size: 40px; /* 가게 이름 크기와 동일하게 설정 */
  background-color: #8B4513; /* 갈색 배경 */
  color: white;
  border: none;
  border-radius: 5px;
  padding: 5px 10px;
  cursor: pointer;
}

.review-button:hover {
  background-color: #a0522d; /* hover 시 색상 변경 */
}
</style>
