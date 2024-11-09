import { defineStore } from 'pinia';
import { fetchReviewsByShopSeq, createReview } from '@/api/review/ReviewApi';
import { fetchShopCardBySeq } from '@/api/shop/ShopReadApi';
import { addLike, deleteLike, checkLikeStatus } from '@/api/review/GoodApi'; // 조회 API 추가

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
                localStorage.setItem('shopData', JSON.stringify(this.shopData));
            } catch (error) {
                console.error('스토어에서 매장 데이터를 로드하는 중 오류 발생:', error);
            }
        },
        async loadReviews(shopSeq) {
            try {
                const response = await fetchReviewsByShopSeq(shopSeq);
                this.reviews = response;
                this.currentShopSeq = shopSeq;

                if (response.length > 0) {
                    const { bookSeq } = response[0];
                    this.bookSeq = bookSeq;
                }

                localStorage.setItem('reviews', JSON.stringify(this.reviews));
            } catch (error) {
                console.error('스토어에서 리뷰 데이터를 로드하는 중 오류 발생:', error);
            }
        },
        async checkLikeStatus(userSeq, reviewSeq) {
            try {
                const response = await checkLikeStatus(this.currentShopSeq, userSeq, reviewSeq);
                const review = this.reviews.find(r => r.reviewSeq === reviewSeq);
                if (review) {
                    review.isClicked = response.isClicked;
                    review.isLiked = response.isClicked === 'Y';
                }
                localStorage.setItem('reviews', JSON.stringify(this.reviews));
            } catch (error) {
                console.error('좋아요 상태 조회 중 오류 발생:', error);
            }
        },
        async addLike(userSeq, reviewSeq) {
            try {
                if (!this.currentShopSeq) {
                    throw new Error('shopSeq가 설정되지 않았습니다.');
                }

                console.log('addLike 호출:', { userSeq, reviewSeq, shopSeq: this.currentShopSeq });

                const response = await addLike(this.currentShopSeq, userSeq, reviewSeq);
                console.log('좋아요가 성공적으로 추가되었습니다:', response);

                const review = this.reviews.find(r => r.reviewSeq === reviewSeq);
                if (review) {
                    review.likeCount += 1;
                    review.isLiked = true;
                    review.isClicked = 'Y';
                }

                localStorage.setItem('reviews', JSON.stringify(this.reviews));
            } catch (error) {
                console.error('좋아요 추가 중 오류 발생:', error);
            }
        },
        async deleteLike(goodSeq, reviewSeq) {
            try {
                if (!this.currentShopSeq) {
                    throw new Error('shopSeq가 설정되지 않았습니다.');
                }

                const review = this.reviews.find(r => r.reviewSeq === reviewSeq);
                if (review && review.isClicked === 'Y') {
                    const response = await deleteLike(this.currentShopSeq, goodSeq);
                    console.log('좋아요가 성공적으로 삭제되었습니다:', response);

                    review.likeCount -= 1;
                    review.isLiked = false;
                    review.isClicked = 'N';
                } else {
                    console.warn('좋아요가 추가되지 않았거나 isClicked가 Y가 아닙니다.');
                }

                localStorage.setItem('reviews', JSON.stringify(this.reviews));
            } catch (error) {
                console.error('좋아요 삭제 중 오류 발생:', error);
            }
        },
    }
});
