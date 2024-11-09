import axios from 'axios';

export const fetchShopList = async() => {
    try {
        const response = await axios.get(`http://localhost:8100/api/v1/shop`);
        const data = response.data.data;
        console.log(data);
        return data;
    } catch (error) {
        console.error('API 호출 중 오류:', error);
        throw error;
    }
}