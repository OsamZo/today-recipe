import { defineStore } from 'pinia';
import { fetchReviewsByShopSeq } from '@/api/review/ReviewReadApi';

export const useReviewStore = defineStore('reviewStore', {
    state: () => ({
        reviews: [],
    }),
    actions: {
        async loadReviews(shopSeq) {
            try {
                const response = await fetchReviewsByShopSeq(shopSeq);
                this.reviews = response;
            } catch (error) {
                console.error('스토어에서 리뷰 데이터를 로드하는 중 오류 발생:', error);
            }
        }
    },
});
