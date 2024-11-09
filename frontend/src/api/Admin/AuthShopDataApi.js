import { ref } from 'vue';
import axios from 'axios';

export function authShopDataApi(shopSeq) {
    const shop = ref({});

    const fetchShops = async () => {
        try {
            const response = await axios.get(`http://localhost:8100/api/v1/admin/shop/${shopSeq}/auth`);
            shop.value = response.data.data;
            console.log(shop.value);
        } catch (error) {
            console.error("데이터를 불러오는 중 오류 발생:", error);
        }
    };

    return {
        shop,
        fetchShops
    };
}
