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
      reviewStore.loadReviews(1);
    });

    const reviews = computed(() => reviewStore.reviews);
    const shopData = computed(() => {
      if (reviews.value.length > 0) {
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
