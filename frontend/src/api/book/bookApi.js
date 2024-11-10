import axios from 'axios';

export const bookProduct = async(productSeq, userSeq, bookQty) => {
    try {
        return await axios.post(`http://localhost:8100/api/v1/product/${productSeq}`,
            null,
            {
                params: {
                    userSeq,
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