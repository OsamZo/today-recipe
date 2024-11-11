import axios from 'axios';

// (사장) 매장 정보 수정 API
export const updateOwnerShop = async (shopSeq, updateShopData) => {
    try {
        // console.log(shopSeq, updateShopData.value);
        await axios.put(`http://localhost:8100/api/v1/owner/shop/${shopSeq}`, updateShopData.value
        );
    } catch (error) {
        console.error('매장 정보를 수정하는 중 오류가 발생했습니다:', error);
        throw error;
    }
}

// (사장) 매장 정보 삭제 API
export const deleteOwnerShop = async (shopSeq) => {
    try {
        await axios.delete(`http://localhost:8100/api/v1/owner/shop/${shopSeq}`);
    } catch (error) {
        console.error('매장 정보를 삭제하는 중 오류가 발생했습니다:', error);
        throw error;
    }

}
