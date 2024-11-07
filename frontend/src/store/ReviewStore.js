import { defineStore } from 'pinia';
import { fetchReviewsByShopSeq, createReview } from '@/api/review/ReviewApi';
import { fetchShopCardBySeq } from '@/api/shop/ShopReadApi';

export const useReviewStore = defineStore('reviewStore', {
    state: () => ({
        reviews: [],
        shopData: JSON.parse(localStorage.getItem('shopData')) || {
            shopImgUrl: '',
            shopName: ''
        },
        bookSeq: null // bookSeq 상태 추가
    }),
    actions: {
        async loadShopData(shopSeq) {
            try {
                const shopInfo = await fetchShopCardBySeq(shopSeq);
                this.shopData = shopInfo;

                // 로컬 스토리지에 저장
                localStorage.setItem('shopData', JSON.stringify(this.shopData));
            } catch (error) {
                console.error('스토어에서 매장 데이터를 로드하는 중 오류 발생:', error);
            }
        },
        async loadReviews(shopSeq) {
            try {
                const response = await fetchReviewsByShopSeq(shopSeq);
                this.reviews = response;

                if (response.length > 0) {
                    const { bookSeq } = response[0];
                    this.bookSeq = bookSeq;
                }

                // 로컬 스토리지에 저장
                localStorage.setItem('reviews', JSON.stringify(this.reviews));
            } catch (error) {
                console.error('스토어에서 리뷰 데이터를 로드하는 중 오류 발생:', error);
            }
        },
        setBookSeq(bookSeq) {
            this.bookSeq = bookSeq;
        },
        async createReview(userSeq, reviewContent) {
            try {
                if (!this.bookSeq) {
                    throw new Error('bookSeq가 설정되지 않았습니다.');
                }

                const reviewData = {
                    reviewContent
                };

                const response = await createReview(userSeq, this.bookSeq, reviewData);
                console.log('리뷰가 성공적으로 추가되었습니다:', response);

                // 리뷰 추가 후 스토어 상태 업데이트 (옵션)
                this.reviews.push({
                    shopImgUrl: this.shopData.shopImgUrl,
                    shopName: this.shopData.shopName,
                    userNickname: '현재 사용자', // 실제 사용자 정보를 바인딩할 수 있습니다.
                    regDate: new Date().toISOString(),
                    reviewContent: reviewContent,
                    likeCount: 0,
                    bookSeq: this.bookSeq
                });

                // 로컬 스토리지에 업데이트된 리뷰 저장
                localStorage.setItem('reviews', JSON.stringify(this.reviews));
            } catch (error) {
                console.error('리뷰 추가 중 오류 발생:', error);
            }
        },
        async addLike(userSeq, reviewSeq, shopSeq) {
            try {
                const response = await addLike(shopSeq, userSeq, reviewSeq);
                console.log('좋아요가 성공적으로 추가되었습니다:', response);

                // 좋아요 추가 후 해당 리뷰의 likeCount 업데이트
                const review = this.reviews.find(r => r.reviewSeq === reviewSeq);
                if (review) {
                    review.likeCount += 1;
                    review.isLiked = true; // UI 상태 업데이트
                }

                // 로컬 스토리지에 업데이트된 리뷰 저장
                localStorage.setItem('reviews', JSON.stringify(this.reviews));
            } catch (error) {
                console.error('좋아요 추가 중 오류 발생:', error);
            }
        },
        async deleteLike(shopSeq, goodSeq, reviewSeq) {
            try {
                const response = await deleteLike(shopSeq, goodSeq);
                console.log('좋아요가 성공적으로 삭제되었습니다:', response);

                // 좋아요 삭제 후 해당 리뷰의 likeCount 업데이트
                const review = this.reviews.find(r => r.reviewSeq === reviewSeq);
                if (review) {
                    review.likeCount -= 1;
                    review.isLiked = false; // UI 상태 업데이트
                }

                // 로컬 스토리지에 업데이트된 리뷰 저장
                localStorage.setItem('reviews', JSON.stringify(this.reviews));
            } catch (error) {
                console.error('좋아요 삭제 중 오류 발생:', error);
            }
        }
    }
});
