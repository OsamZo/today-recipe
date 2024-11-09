import {defineStore} from "pinia";
import {fetchOwnerShopInfo} from "@/api/shop/ShopReadApi.js";

export const useShopStore = defineStore('ShopStore', {
    state: () => ({
        shopData: JSON.parse(localStorage.getItem('shopData')) || {
            userSeq: '',
            shopSeq: '',
            shopName: '',
            shopAddress: '',
            shopIntroduction: ''
        }
    }),
    actions: {
        async loadOnwerShopData(){
            try {
                const shopInfo = await fetchOwnerShopInfo();
                this.shopData = shopInfo;

                // 로컬 스토리지에 저장
                localStorage.setItem('shopData', JSON.stringify(this.shopData));
            } catch (error) {
                console.log('스토어에서 매장 데이터를 로드하는 중 오류 발생 :', error);
            }
        }


    }
});