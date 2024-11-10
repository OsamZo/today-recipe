import axios from 'axios';

// 매장 전체 목록 가져오기 API
export const fetchShopList = async() => {
    try {
        const response = await axios.get(`http://localhost:8100/api/v1/shop`);
        return response.data.data;
    } catch (error) {
        console.error('API 호출 중 오류:', error);
        return [];
    }
}

// 카테고리별 매장 목록 가져오기 API
export const fetchShopListByCategory = async (categorySeq, orderBy, searchKeyword) => {
    try {
        const response = await axios.get(`http://localhost:8100/api/v1/category/${categorySeq}/shop`, {
            params: {
                orderBy: orderBy,
                searchKeyword: searchKeyword
            }
        });
        return response.data.data;
    } catch (error) {
        console.error("매장 리스트를 불러오던 중 오류 발생", error);
        return [];
    }
};

// 카테고리 목록 가져오기 API
export const fetchCategoryList = async () => {
    try {
        const response = await axios.get(`http://localhost:8100/api/v1/category`);
        return response.data.data;
    } catch (error) {
        console.error("카테고리 리스트를 불러오던 중 오류 발생", error);
        return [];
    }
};

// 오늘의 특가 리스트 가져오기 API
export const fetchTodaySaleList = async() => {
    try {
        const response = await axios.get(`http://localhost:8100/api/v1/shop/today`);
        return response.data.data;
    } catch(error) {
        console.log("오늘의 특가 리스트를 불러오던 중 오류 발생", error);
        return [];
    }
}
