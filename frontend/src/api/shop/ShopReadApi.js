import axios from 'axios';

// 매장 이미지, 매장 이름 정보를 가져오는 API
export const fetchShopCardBySeq = async (shopSeq) => {
    try {
        const response = await axios.get(`http://localhost:8100/api/v1/shop/${shopSeq}`);
        return response.data.data;
    } catch (error) {
        console.error('매장 정보를 가져오는 중 오류가 발생했습니다:', error);
        throw error;
    }
};
