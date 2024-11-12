<template>
  <ReviewPageContent>
    <ShopCard
      :shopImgUrl="shopData.shopImgUrl"
      :shopName="shopData.shopName"
    >
      <template v-slot:action>
        <button class="review-button" @click="goToReviewAdd">리뷰 작성</button>
      </template>
    </ShopCard>
    <div class="separator"></div>
    <ReviewList :reviews="reviews" />
  </ReviewPageContent>
</template>

<script setup>
import { useReviewStore } from '@/store/ReviewStore';
import { useUserStore } from '@/store/UserStore';
import { onMounted, computed, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import ShopCard from '@/components/ShopCard.vue';
import ReviewList from '@/components/ReviewList.vue';
import ReviewPageContent from '@/components/WhiteContentBox.vue';

const router = useRouter();
const route = useRoute();
const reviewStore = useReviewStore();
const userStore = useUserStore();
const shopSeq = route.params.shopSeq;

onMounted(() => {
  if (shopSeq) {
    reviewStore.loadShopData(shopSeq);
    reviewStore.loadReviews(shopSeq);
  } else {
    console.error('shopSeq가 없습니다. URL을 확인하세요.');
  }
});

watch(
  () => userStore.userSeq,
  async (newSeq) => {
    if (newSeq) {
      for (let review of reviewStore.reviews) {
        try {
          await reviewStore.checkLikeStatus(newSeq, review.reviewSeq);
        } catch (error) {
          console.error(`리뷰 ${review.reviewSeq}의 좋아요 상태 확인 중 오류 발생:`, error);
        }
      }
    }
  }
);

const reviews = computed(() => reviewStore.reviews);
const shopData = computed(() => reviewStore.shopData);

const goToReviewAdd = () => {
  router.push('/review/add');
};
</script>

<style scoped>
.review-button {
  background-color: #8B4513; 
  color: white;
  border: none;
  border-radius: 5px;
  padding: 5px 15px; 
  cursor: pointer;
  margin-left: 40px;
  font-size: 20px; 
}

.review-button:hover {
  background-color: #a0522d;
}

.separator {
  width: 100%; 
  height: 5px;
  background-color: #8B4513;
  margin-top: 200px; 
  margin-bottom: 20px; 
  border-radius: 5px;
}
</style>
