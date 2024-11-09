import { defineStore } from 'pinia';
import { fetchReviewsByShopSeq, createReview } from '@/api/review/ReviewApi';
import { fetchShopCardBySeq } from '@/api/shop/ShopReadApi';
import { addLike, deleteLike, checkLikeStatus } from '@/api/review/GoodApi';
import { useUserStore } from '@/store/UserStore';

export const useReviewStore = defineStore('reviewStore', {
    state: () => ({
        reviews: [],
        shopData: JSON.parse(localStorage.getItem('shopData')) || {
            shopImgUrl: '',
            shopName: ''
        },
        bookSeq: null,
        currentShopSeq: null,
    }),
    actions: {
        async loadShopData(shopSeq) {
            try {
                const shopInfo = await fetchShopCardBySeq(shopSeq);
                this.shopData = shopInfo;
                this.currentShopSeq = shopSeq;
                localStorage.setItem('shopData', JSON.stringify(this.shopData));
            } catch (error) {
                console.error('스토어에서 매장 데이터를 로드하는 중 오류 발생:', error);
            }
        },
        async loadReviews(shopSeq) {
            try {
                this.currentShopSeq = shopSeq;
                const response = await fetchReviewsByShopSeq(shopSeq);
                this.reviews = response;

                const userStore = useUserStore();
                if (userStore.userSeq) {
                    for (let review of this.reviews) {
                        await this.checkLikeStatus(userStore.userSeq, review.reviewSeq);
                    }
                }

            } catch (error) {
                console.error('스토어에서 리뷰 데이터를 로드하는 중 오류 발생:', error);
            }
        },
        async checkLikeStatus(userSeq, reviewSeq) {
            try {
                const likeStatus = await checkLikeStatus(this.currentShopSeq, userSeq, reviewSeq);
                const review = this.reviews.find(r => r.reviewSeq === reviewSeq);
                if (review) {
                    Object.assign(review, {
                        isLiked: !!likeStatus.goodSeq,
                        goodSeq: likeStatus.goodSeq || null
                    });
                }
                this.reviews = [...this.reviews];
            } catch (error) {
                console.error('좋아요 상태 확인 중 오류 발생:', error);
            }
        },
        async addLike(userSeq, reviewSeq) {
            try {
                const response = await addLike(this.currentShopSeq, userSeq, reviewSeq);
                const review = this.reviews.find(r => r.reviewSeq === reviewSeq);
                if (review) {
                    review.isLiked = true;
                    review.goodSeq = response.goodSeq;
                    review.likeCount += 1;
                }
                this.reviews = [...this.reviews];
                window.location.reload();
            } catch (error) {
                console.error('좋아요 추가 중 오류 발생:', error);
                throw error;
            }
        },
        async deleteLike(reviewSeq) {
            try {
                const review = this.reviews.find(r => r.reviewSeq === reviewSeq);
                if (review && review.goodSeq) {
                    const userStore = useUserStore();
                    const userSeq = userStore.userSeq;

                    if (!userSeq) {
                        throw new Error('유효하지 않은 사용자입니다.');
                    }

                    await deleteLike(this.currentShopSeq, review.goodSeq, userSeq);
                    review.isLiked = false;
                    review.likeCount -= 1;
                    review.goodSeq = null;
                }
                this.reviews = [...this.reviews];
                window.location.reload();
            } catch (error) {
                console.error('좋아요 삭제 중 오류 발생:', error);
                throw error;
            }
        },
    }
});
