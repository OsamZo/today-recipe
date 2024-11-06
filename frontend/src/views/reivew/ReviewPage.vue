<template>
  <ReviewPageContent>
    <ShopCard>
      <template v-slot:action>
        <button class="review-button">+ 리뷰 작성</button> <!-- 슬롯에 버튼 추가 -->
      </template>
    </ShopCard>
    <div class="separator"></div>
    <ReviewList />
  </ReviewPageContent>
</template>

<script>
import ShopCard from '@/components/ShopCard.vue';
import ReviewList from '@/components/ReviewList.vue';
import ReviewPageContent from '@/components/WhiteContentBox.vue';

export default {
  components: {
    ShopCard,
    ReviewList,
    ReviewPageContent
  }
};
</script>

<style scoped>
.review-button {
  background-color: #8B4513; /* 갈색 배경 */
  color: white;
  border: none;
  border-radius: 5px;
  padding: 5px 15px; /* 패딩 조정 */
  cursor: pointer;
  margin-left: 40px; /* 위치 조정 */
  font-size: 20px; /* 가게 이름의 크기와 동일하게 설정 */
}

.review-button:hover {
  background-color: #a0522d; /* hover 시 색상 변경 */
}

.separator {
  width: 100%; 
  height: 5px;
  background-color: #8B4513;
  margin-top: 150px; 
  margin-bottom: 20px; 
  border-radius: 5px;
}
</style>
