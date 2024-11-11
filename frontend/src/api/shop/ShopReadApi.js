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

// (사장) 본인의 매장 정보 조회 API
export const fetchOwnerShopInfo = async (userSeq) => {
    try {
        const response = await axios.get(`http://localhost:8100/api/v1/owner/${userSeq}/shop`);
        return response.data.data;
    } catch (error) {
        console.log('매장 정보를 가져오는 중 오류가 발생했습니다. :', error);
        throw error;
    }
}

// 매장 상세 정보를 가져오는 API
export const fetchShopDetail = async(categorySeq, shopSeq) => {
    try {
        const response = await axios.get(`http://localhost:8100/api/v1/category/${categorySeq}/shop/${shopSeq}`);
        return response.data.data;
    } catch(error) {
        console.log("가게 상세 정보를 불러오는 데 오류 발생", error);
        throw error;
    }
}

