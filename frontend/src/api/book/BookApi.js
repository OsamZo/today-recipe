import axios from 'axios';

export const bookProduct = async(productSeq, userSeq, bookQty) => {
    try {
        return await axios.post(`http://localhost:8100/api/v1/user/${userSeq}/product/${productSeq}`,
            null,
            {
                params: {
                    bookQty,
                },
            });
    }  catch (error) {
        if (error.response.status === 400 && error.response.data.errorCode === 'BOOK_ERROR_003') {
            alert('이미 오늘 예약한 상품이 있습니다. 중복 예약이 불가능합니다.');
        } else {
            alert('예약에 실패했습니다. 잠시 후 다시 시도해주세요.');
        }
        throw error;
    }
}

export const cancelBookingProduct = async(bookSeq) => {
    try {
        return await axios.put(`http://localhost:8100/api/v1/book/${bookSeq}`);
    } catch(error) {
        console.log("예약 취소를 하던 도중 에러 발생", error);
    }
}

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
export const updateBookStatus = async (bookSeq, bookIsProductReceived) => {
    try {
        // console.log(bookSeq, updateBookStatus.value);
        await axios.put(`http://localhost:8100/api/v1/owner/book/${bookSeq}`,
            bookIsProductReceived,
            { headers: { 'Content-Type': 'application/json' }});
    } catch (error) {
        console.log('예약 목록 정보를 수정하는 중 오류가 발생했습니다.', error);
        throw error;
    }
}