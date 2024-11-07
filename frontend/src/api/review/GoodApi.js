import axios from 'axios';

export async function addLike(shopSeq, userSeq, reviewSeq) {
    try {
        const response = await axios.post(`/api/v1/shop/${shopSeq}/review/good`, {
            userSeq,
            reviewSeq
        });
        return response.data;
    } catch (error) {
        console.error('좋아요 추가 중 오류 발생:', error);
        throw error;
    }
}

export async function deleteLike(shopSeq, goodSeq) {
    try {
        const response = await axios.delete(`/api/v1/shop/${shopSeq}/review/good/${goodSeq}`);
        return response.data;
    } catch (error) {
        console.error('좋아요 삭제 중 오류 발생:', error);
        throw error;
    }
}
