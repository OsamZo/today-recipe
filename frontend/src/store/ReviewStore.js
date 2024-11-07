import { defineStore } from 'pinia';
import { fetchReviewsByShopSeq } from '@/api/review/ReviewReadApi';

export const useReviewStore = defineStore('reviewStore', {
    state: () => ({
        reviews: [],
        shopData: JSON.parse(localStorage.getItem('shopData')) || { // 로컬 스토리지에서 초기값 설정
            shopImgUrl: '', 
            shopName: ''
        }
    }),
    actions: {
        async loadReviews(shopSeq) {
            try {
                const response = await fetchReviewsByShopSeq(shopSeq);
                this.reviews = response;

                // 첫 번째 리뷰에서 필요한 값만 추출하여 shopData에 설정
                if (this.reviews.length > 0) {
                    const { shopImgUrl, shopName } = this.reviews[0]; // API 응답에서 필요한 값만 추출
                    this.shopData = {
                        shopImgUrl,
                        shopName
                    };

                    // 로컬 스토리지에 shopData 저장
                    localStorage.setItem('shopData', JSON.stringify(this.shopData));
                }
            } catch (error) {
                console.error('스토어에서 리뷰 데이터를 로드하는 중 오류 발생:', error);
            }
        },
        setShopData(shopImgUrl, shopName) {
            this.shopData = {
                shopImgUrl,
                shopName
            };

            // 로컬 스토리지에 shopData 저장
            localStorage.setItem('shopData', JSON.stringify(this.shopData));
        }
    }
});
