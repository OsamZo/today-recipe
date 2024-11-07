<template>
  <ReviewPageContent>
    <ShopCard
      :shopImgUrl="shopData.shopImgUrl"
      :shopName="shopData.shopName"
    >
      <template v-slot:action>
        <button class="review-button">리뷰 작성</button>
      </template>
    </ShopCard>
    <div class="separator"></div>
    <ReviewList :reviews="reviews" />
  </ReviewPageContent>
</template>

<script>
import { useReviewStore } from '@/store/ReviewStore';
import { onMounted, computed } from 'vue';
import ShopCard from '@/components/ShopCard.vue';
import ReviewList from '@/components/ReviewList.vue';
import ReviewPageContent from '@/components/WhiteContentBox.vue';

export default {
  components: {
    ShopCard,
    ReviewList,
    ReviewPageContent
  },
  setup() {
    const reviewStore = useReviewStore();

    onMounted(() => {
      reviewStore.loadReviews(1); // shopSeq 값이 올바른지 확인
      console.log('Reviews after loading:', reviews.value); // 데이터 확인
    });

    // 스토어에서 데이터를 가져오기
    const reviews = computed(() => reviewStore.reviews);
    const shopData = computed(() => {
      if (reviews.value.length > 0) {
        // 첫 번째 리뷰에서 가게 정보를 가져온다고 가정
        return {
          shopImgUrl: reviews.value[0].shopImgUrl,
          shopName: reviews.value[0].shopName
        };
      }
      return {
        shopImgUrl: '',
        shopName: ''
      };
    });

    return {
      reviews,
      shopData
    };
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
