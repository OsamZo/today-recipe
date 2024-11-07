import { defineStore } from 'pinia';
import { fetchReviewsByShopSeq, createReview } from '@/api/review/ReviewApi';

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
        async loadReviews(shopSeq) {
            try {
                const response = await fetchReviewsByShopSeq(shopSeq);
                this.reviews = response;

                if (this.reviews.length > 0) {
                    const { shopImgUrl, shopName, bookSeq } = this.reviews[0];
                    this.shopData = { shopImgUrl, shopName };
                    this.bookSeq = bookSeq; // bookSeq 저장

                    localStorage.setItem('shopData', JSON.stringify(this.shopData));
                }
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

                // 리뷰 데이터를 준비
                const reviewData = {
                    reviewContent
                };

                // 리뷰 추가 API 호출
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
            } catch (error) {
                console.error('리뷰 추가 중 오류 발생:', error);
            }
        }
    }
});
