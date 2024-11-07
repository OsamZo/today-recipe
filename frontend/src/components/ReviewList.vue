<template>
    <div class="review-list">
        <div v-for="(review, index) in reviews" :key="index" class="review-item">
            <div class="review-header">
                <div class="review-info">
                    <span class="review-user">{{ review.userNickname }} 님</span>
                    <span class="review-date">{{ review.regDate }}</span>
                </div>
                <div class="review-actions">
                    <!-- <button class="action-btn gray-btn">수정</button>
                    <button class="action-btn gray-btn">삭제</button> -->
                    <button @click="toggleLike(review)" class="action-btn like-btn">
                        ❤️ <span class="like-count">{{ review.likeCount }}</span>
                    </button>
                </div>
            </div>
            <p class="review-content">{{ review.reviewContent }}</p>
        </div>
    </div>
</template>

<script>
import { useReviewStore } from '@/store/ReviewStore';

export default {
    props: {
        reviews: {
            type: Array,
            required: true
        }
    },
    setup() {
        const reviewStore = useReviewStore();

        const toggleLike = (review) => {
            if (review.isLiked) {
                reviewStore.deleteLike(review.shopSeq, review.goodSeq, review.reviewSeq);
            } else {
                reviewStore.addLike(review.userSeq, review.reviewSeq, review.shopSeq);
            }
        };

        return {
            toggleLike
        };
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
