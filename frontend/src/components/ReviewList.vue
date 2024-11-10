<template>
    <div class="review-list">
        <div v-for="(review, index) in reviews" :key="index" class="review-item">
            <div class="review-header">
                <div class="review-info">
                    <span class="review-user">{{ review.userNickname }} 님</span>
                    <span class="review-date">{{ review.regDate }}</span>
                </div>
                <div class="review-actions">
                    <button 
                        v-if="!review.isLiked"
                        @click="addLike(review)" 
                        class="action-btn like-btn"
                    >
                        <span class="heart-icon">🤍</span>
                        <span class="like-count">{{ review.likeCount }}</span>
                    </button>
                    
                    <button 
                        v-else
                        @click="deleteLike(review)" 
                        class="action-btn like-btn liked"
                    >
                        <span class="heart-icon">❤️</span>
                        <span class="like-count">{{ review.likeCount }}</span>
                    </button>
                </div>
            </div>
            <p class="review-content">{{ review.reviewContent }}</p>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';
import { useReviewStore } from '@/store/ReviewStore';
import { useUserStore } from '@/store/UserStore';

const route = useRoute();
const reviewStore = useReviewStore();
const userStore = useUserStore();
const reviews = ref([]);

// URL에서 shopSeq 가져오기
const shopSeq = route.params.shopSeq || 1;

onMounted(async () => {
    try {
        await reviewStore.loadReviews(shopSeq);
        reviews.value = reviewStore.reviews;

        if (userStore.userSeq) {
            checkLikeStatuses();
        }
    } catch (error) {
        console.error('리뷰 목록 불러오기 중 오류 발생:', error);
    }
});

watch(
    () => userStore.userSeq,
    async (newSeq) => {
        if (newSeq) {
            await checkLikeStatuses();
        }
    }
);

const checkLikeStatuses = async () => {
    for (let review of reviews.value) {
        try {
            await reviewStore.checkLikeStatus(userStore.userSeq, review.reviewSeq);
        } catch (error) {
            console.error(`리뷰 ${review.reviewSeq}의 좋아요 상태 확인 중 오류 발생:`, error);
        }
    }
    reviews.value = [...reviews.value];
};

const addLike = async (review) => {
    if (!userStore.userSeq) {
        alert('로그인이 필요합니다.');
        return;
    }

    try {
        await reviewStore.addLike(userStore.userSeq, review.reviewSeq);
        review.isLiked = true;
    } catch (error) {
        console.error('좋아요 추가 중 오류 발생:', error);
    }
};

const deleteLike = async (review) => {
    if (!userStore.userSeq) {
        alert('로그인이 필요합니다.');
        return;
    }

    if (!review.goodSeq) {
        console.error('삭제할 좋아요의 goodSeq가 없습니다.');
        return;
    }

    try {
        await reviewStore.deleteLike(review.reviewSeq);
        review.isLiked = false;
        review.goodSeq = null;
    } catch (error) {
        console.error('좋아요 삭제 중 오류 발생:', error);
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
.like-btn {
    color: #888;
    display: flex;
    align-items: center;
    text-decoration: none;
}
.like-btn .heart-icon {
    font-size: 1.2rem;
}
.like-btn.liked .heart-icon {
    color: #e63946;
}
.like-btn:not(.liked) .heart-icon {
    color: #ccc;
}
.review-content {
    line-height: 1.5;
}
</style>
