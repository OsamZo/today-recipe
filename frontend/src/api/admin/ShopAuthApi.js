import { ref } from "vue";
import axios from "axios";
import router from "@/router/index.js";

export function shopAuthApi() {
    const shopList = ref([]);


    const fetchShops = async (page, size) => {
        try {
            const response = await axios.get("http://localhost:8100/api/v1/admin/shop/auth", {
                params: { page, size }
            });
            shopList.value = response.data.data;
        } catch (error) {
            console.error("데이터를 불러오는 중 오류 발생:", error);
        }
    };

    const handleShopAuth = (shopSeq) => {
        router.push(`/admin/shopAuth/${shopSeq}/auth`);
    };

    return { shopList, fetchShops, handleShopAuth };
}
