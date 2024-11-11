import axios from 'axios';

// (사장) 내 매장 예약 목록 조회 API
export const fetchOwnerShopBooks = async (userSeq) => {
    try {
        // console.log(userSeq); 로그 정상
        const response = await axios.get(`http://localhost:8100/api/v1/owner/${userSeq}/book`);
        console.log(response.data.data); //로그 정상
        return response.data.data;
    } catch (error) {
        console.log("예약 목록 정보를 불러오는 데 오류 발생", error);
        return [];
    }
}

// (사장) 내 매장 예약 정보 수정 API
export const updateBookStatus = async (bookSeq, updateBookStatus) => {
    try {
        // console.log(bookSeq, updateBookStatus.value);
        await axios.put(`http://localhost:8100/api/v1/owner/book/${bookSeq}`, updateBookStatus);
    } catch (error) {
        console.log('예약 목록 정보를 수정하는 중 오류가 발생했습니다.', error);
        throw error;
    }
}