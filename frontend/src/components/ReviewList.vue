<template>
    <div class="review-list">
        <div v-for="(review, index) in reviews" :key="index" class="review-item">
            <div class="review-header">
                <div class="review-info">
                    <span class="review-user">{{ review.userNickname }} 님</span>
                    <span class="review-date">{{ review.regDate }}</span>
                </div>
                <div class="review-actions">
                    <button @click="toggleLike(review)" class="action-btn like-btn">
                        ❤️ <span class="like-count">{{ review.likeCount }}</span>
                    </button>
                </div>
            </div>
            <p class="review-content">{{ review.reviewContent }}</p>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useReviewStore } from '@/store/ReviewStore';
import { useUserStore } from '@/store/UserStore';

const route = useRoute();
const reviewStore = useReviewStore();
const userStore = useUserStore();
const reviews = ref([]);

// URL에서 shopSeq 가져오기
const shopSeq = route.params.shopSeq || 1; // 기본값 1 설정

onMounted(async () => {
    try {
        // shopSeq를 사용하여 리뷰 리스트 불러오기
        await reviewStore.loadReviews(shopSeq);
        reviews.value = reviewStore.reviews;

        // 각 리뷰의 좋아요 상태 조회
        if (userStore.userSeq) {
            for (let review of reviews.value) {
                try {
                    const isLiked = await reviewStore.checkLikeStatus(userStore.userSeq, review.reviewSeq);
                    review.isLiked = isLiked; // 좋아요 상태 설정
                } catch (error) {
                    console.error(`리뷰 ${review.reviewSeq}의 좋아요 상태 조회 중 오류 발생:`, error);
                }
            }
        }
    } catch (error) {
        console.error('리뷰 목록 불러오기 중 오류 발생:', error);
    }
});

const toggleLike = async (review) => {
    if (!userStore.userSeq) {
        alert('로그인이 필요합니다.');
        return;
    }

    try {
        if (review.isLiked === 'Y') {
            // 좋아요 삭제
            await reviewStore.deleteLike(review.goodSeq, review.reviewSeq);
            review.isLiked = 'N'; // 상태 업데이트
            review.likeCount -= 1; // likeCount 감소
        } else {
            // 좋아요 추가
            await reviewStore.addLike(userStore.userSeq, review.reviewSeq, shopSeq);
            review.isLiked = 'Y'; // 상태 업데이트
            review.likeCount += 1; // likeCount 증가
        }

        localStorage.setItem('reviews', JSON.stringify(reviews.value));
    } catch (error) {
        console.error('좋아요 상태 변경 중 오류 발생:', error);
    }
};
</script>

<style scoped>
.review-list {
    margin-top: 20px;
    width: 100%;
}
.review-item {
    width: 100%;
    margin-bottom: 30px;
    border-bottom: 1px solid #ddd;
    padding-bottom: 30px;
}
.review-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 3vh;
}
.review-info {
    display: flex;
    align-items: center;
}
.review-user {
    font-weight: bold;
    margin-right: 5px;
}
.review-date {
    font-size: 0.8rem;
    margin-left: 50px;
    color: #888;
}
.review-actions {
    display: flex;
    gap: 10px;
}
.action-btn {
    background: none;
    border: none;
    cursor: pointer;
    font-size: 0.9rem;
}
.action-btn:hover {
    text-decoration: underline;
}
.gray-btn {
    color: #888; 
}
.like-btn {
    color: #e63946; 
    display: flex;
    align-items: center;
    text-decoration: none;
}
.like-btn:hover {
    text-decoration: none; 
}
.like-count {
    margin-left: 5px;
    color: #888;
    font-weight: bold;
}
.review-content {
    line-height: 1.5; 
}
</style>
