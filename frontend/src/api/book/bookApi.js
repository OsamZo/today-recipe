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
    } catch(error) {
        console.log("예약에 실패했습니다.", error);
    }
}