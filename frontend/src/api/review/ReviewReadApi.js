import axios from 'axios';

export const fetchReviewsByShopSeq = async (shopSeq) => {
    try {
        console.log('API 요청 URL:', `/api/v1/shop/${shopSeq}/review`);
        const response = await axios.get(`http://localhost:8100/api/v1/shop/${shopSeq}/review`);
        console.log('API Response:', response.data); // 응답 데이터 확인
        return response.data.data;
    } catch (error) {
        console.error('리뷰 데이터를 가져오는 중 오류가 발생했습니다:', error);
        throw error; 
    }
};
