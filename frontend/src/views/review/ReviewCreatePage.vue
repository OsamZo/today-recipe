<template>
    <ReviewPageContent>
        <ShopCard
            :shopImgUrl="shopData.shopImgUrl"
            :shopName="shopData.shopName"
        >
        </ShopCard>
        <div class="review-input-container">
            <textarea class="review-textarea" placeholder="리뷰를 작성해주세요..."></textarea>
        </div>
        <button class="save-button">저장하기</button>
    </ReviewPageContent>
</template>

<script>
import { useReviewStore } from '@/store/ReviewStore';
import { onMounted, computed } from 'vue';
import ReviewPageContent from '@/components/WhiteContentBox.vue';
import ShopCard from '@/components/ShopCard.vue';

export default {
    components: {
        ShopCard,
        ReviewPageContent
    },
    setup() {
        const reviewStore = useReviewStore();

        onMounted(() => {
            reviewStore.loadShopData(1); // 가게 정보를 가져오는 새로운 메서드
        });

        // 스토어에서 가져온 shopData를 사용
        const shopData = computed(() => reviewStore.shopData || { shopImgUrl: '', shopName: '' });

        return {
            shopData
        };
    }
};
</script>

<style scoped>
.review-input-container {
    margin-top: 200px;
    background-color: #fff8dc; /* 연한 노란색 */
    border-radius: 8px;
    padding: 20px;
    width: 100%;
    box-sizing: border-box;
}

.review-textarea {
    width: 100%;
    height: 150px;
    border: none;
    outline: none;
    resize: none;
    background-color: transparent;
    font-size: 16px;
}

.save-button {
    display: block;
    margin: 30px auto 0; /* 노란색 배경과의 간격 조정 */
    background-color: #8B4513; /* 갈색 */
    color: white;
    border: none;
    border-radius: 5px;
    padding: 10px 20px;
    cursor: pointer;
    font-size: 16px;
    margin-bottom: 100px;
}

.save-button:hover {
    background-color: #a0522d; /* hover 시 색상 */
}
</style>
