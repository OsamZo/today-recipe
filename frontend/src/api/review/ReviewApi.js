import axios from 'axios';

// 매장 리뷰 조회 API
export const fetchReviewsByShopSeq = async (shopSeq) => {
    try {
        const response = await axios.get(`http://localhost:8100/api/v1/shop/${shopSeq}/review`);
        return response.data.data;
    } catch (error) {
        console.error('리뷰 데이터를 가져오는 중 오류가 발생했습니다:', error);
        throw error; 
    }
};

// 리뷰 추가 API
export const createReview = async (userSeq, bookSeq, reviewData) => {
    try {
        const response = await axios.post(`http://localhost:8100/api/v1/user/${userSeq}/book/${bookSeq}`, reviewData);
        return response.data;
    } catch (error) {
        console.error('리뷰를 추가하는 중 오류가 발생했습니다:', error);
        throw error;
    }
};


