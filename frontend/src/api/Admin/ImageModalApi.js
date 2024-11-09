// composables/ImageModalApi.js
import { ref } from 'vue';
import axios from 'axios';

export function imageModalApi(shopSeq) {
    const showModal = ref(false);
    const imgUrl = ref("");

    const fetchImageUrl = async () => {
        if (!shopSeq) {
            console.error("shopSeq 값이 없습니다.");
            return;
        }
        try {
            const response = await axios.get(`http://localhost:8100/api/v1/admin/shop/${shopSeq}/auth/img`, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            imgUrl.value = response.data.data.shopBusinessImgUrl;
            console.log(imgUrl.value);
        } catch (error) {
            console.error("이미지 URL을 불러오는 중 오류 발생:", error);
        }
    };

    const openImgModal = async () => {
        await fetchImageUrl();
        showModal.value = true;
    };

    const closeModal = () => {
        showModal.value = false;
    };

    return {
        showModal,
        imgUrl,
        openImgModal,
        closeModal
    };
}
