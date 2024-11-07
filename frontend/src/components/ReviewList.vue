<template>
    <div class="review-list">
        <div v-for="(review, index) in reviews" :key="index" class="review-item">
            <div class="review-header">
                <div class="review-info">
                    <span class="review-user">{{ review.user }} 님</span>
                    <span class="review-date">{{ review.date }}</span>
                </div>
                <div class="review-actions">
                    <button class="action-btn gray-btn">수정</button>
                    <button class="action-btn gray-btn">삭제</button>
                    <button class="action-btn like-btn">
                        ❤️ <span class="like-count">{{ review.likes }}</span>
                    </button>
                </div>
            </div>
            <p class="review-content">{{ review.content }}</p>
        </div>
    </div>
</template>
  
<script>
    export default {
        data() {
            return {
                reviews: [
                    { user: '망고자두쥬쥬', date: '2024.10.31', content: '산간지역에 좋은 음식을 먹을 수 있어서 좋아요~ 다음에도 들릴 예정입니다. 산간지역에 좋은 음식을 먹을 수 있어서 좋아요~ 다음에도 들릴 예정입니다.', likes: 3 },
                    { user: '망망고고', date: '2024.10.31', content: '산간지역에 좋은 음식을 먹을 수 있어서 좋아요~ 다음에도 들릴 예정입니다.', likes: 1 },
                    // 더 많은 리뷰를 추가할 수 있습니다.
                ]
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
    color: #888; /* 회색 */
}
.like-btn {
    color: #e63946; /* 하트 모양의 빨간색 */
    display: flex;
    align-items: center;
    text-decoration: none;
}
.like-btn:hover {
    text-decoration: none; /* 좋아요 버튼에 언더라인 제거 */
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
