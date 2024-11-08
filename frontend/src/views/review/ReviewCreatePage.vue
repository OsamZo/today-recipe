<template>
    <ReviewPageContent>
        <ShopCard
            :shopImgUrl="shopData.shopImgUrl"
            :shopName="shopData.shopName"
        />
        <div class="review-input-container">
            <textarea v-model="reviewContent" class="review-textarea" placeholder="리뷰를 작성해주세요..."></textarea>
        </div>
        <button class="save-button" @click="saveReview">저장하기</button>
    </ReviewPageContent>
</template>

<script setup>
import { useReviewStore } from '@/store/ReviewStore';
import { onMounted, computed, ref } from 'vue';
import ReviewPageContent from '@/components/WhiteContentBox.vue';
import ShopCard from '@/components/ShopCard.vue';

const reviewStore = useReviewStore();
const reviewContent = ref('');

onMounted(() => {
    reviewStore.loadShopData(1);
});

const shopData = computed(() => reviewStore.shopData || { shopImgUrl: '', shopName: '' });

const saveReview = () => {
    const userSeq = 1; 
    if (reviewContent.value.trim() === '') {
        alert('리뷰 내용을 입력해주세요.');
        return;
    }
    
    reviewStore.createReview(userSeq, reviewContent.value)
        .then(() => {
            alert('리뷰가 성공적으로 등록되었습니다.');
            reviewContent.value = ''; 
        })
        .catch((error) => {
            console.error('리뷰 저장 중 오류 발생:', error);
            alert('리뷰 저장 중 문제가 발생했습니다.');
        });
};
</script>

<style scoped>
.review-input-container {
    margin-top: 200px;
    background-color: #fff8dc; 
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
    margin: 30px auto 0; 
    background-color: #8B4513; 
    color: white;
    border: none;
    border-radius: 5px;
    padding: 10px 20px;
    cursor: pointer;
    font-size: 16px;
    margin-bottom: 100px;
}

.save-button:hover {
    background-color: #a0522d; 
}
</style>
