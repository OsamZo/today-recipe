import { defineStore } from 'pinia';
import axios from 'axios';

export const adminShopStore = defineStore('shopStore', {
    state: () => ({
        shopList: [],
        searchQuery: '',
        showModal: false,
        allShops: [],
    }),

    actions: {
        async fetchShops() {
            try {
                const response = await axios.get('http://localhost:8100/api/v1/admin/shop');
                const filteredShops = response.data.data.filter((shop) => shop.shopDelStatus !== 'Y');
                this.allShops = filteredShops;
                this.shopList.splice(0, this.shopList.length, ...filteredShops);
            } catch (error) {
                console.error('데이터를 불러오는 중 오류 발생', error);
            }
        },

        searchShops() {
            if (!this.searchQuery) {
                this.shopList = [...this.allShops];
            } else {
                this.shopList = this.allShops.filter((shop) =>
                    shop.shopName.toLowerCase().includes(this.searchQuery.toLowerCase())
                );
            }
        },

        openDeleteModal(shopSeq) {
            this.shopSeqToDelete = shopSeq;
            this.showModal = true;
        },

        async confirmDeleteShop() {
            try {
                await axios.delete(`http://localhost:8100/api/v1/admin/shop/${this.shopSeqToDelete}`);
                this.fetchShops();
                this.showModal = false;
            } catch (error) {
                console.error('삭제 요청 중 오류 발생:', error.response?.data || error);
            }
        },

        closeModal() {
            this.showModal = false;
            this.shopSeqToDelete = null;
        },

        initializeSearch() {
            this.searchShops();
        }
    },

    persist: {
        paths: ['searchQuery'],
    },
});
