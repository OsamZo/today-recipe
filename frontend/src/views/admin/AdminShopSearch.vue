<script setup>
import { adminShopStore } from '@/store/AdminShopStore.js';
import AdminNav from "@/components/AdminNav.vue";
import AdminSearchBodyTitle from "@/components/AdminSearchBodyTitle.vue";
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import { createPinia } from 'pinia';
import piniaPersist from 'pinia-plugin-persist';

const shopStore = adminShopStore();
const router = useRouter();
const pinia = createPinia();
pinia.use(piniaPersist);

// 검색 상태
const searchQuery = ref('');

const goToShopReview = (shopSeq) => {
  router.push(`/admin/shopList/review/${shopSeq}`);
};


onMounted(async () => {
  await shopStore.fetchShops();  // 데이터를 항상 로드
  shopStore.initializeSearch();  // 검색 초기화 호출
});

const handleSearchKeyPress = (event) => {
  if (event.key === "Enter") {
    shopStore.searchShops();
  }
};
</script>


<template>
  <div class="main-header">
    <p class="title">관리자 페이지</p>
  </div>
  <div class="admin-container">
    <div class="content-box">
      <AdminNav class="nav"/>
      <div class="main-content">
        <AdminSearchBodyTitle/>
        <div class="search-box">
          <input
              v-model="shopStore.searchQuery"
              type="text"
              placeholder="찾고있는 정보가 있나요?"
              @keydown="handleSearchKeyPress"
          />
        </div>

        <div class="content">
          <div class="shop-item" v-for="shop in shopStore.shopList" :key="shop.shopSeq">
            <img :src="shop.shopImgUrl" alt="매장 이미지" class="store-image"/>
            <div class="shop-info" @click="goToShopReview(shop.shopSeq)">
              <p class="shop-name">{{ shop.shopName }}</p>
              <p class="shop-category">{{ shop.categoryName }}</p>
              <p class="shop-address">매장 주소 | {{ shop.shopAddress }}</p>
            </div>
            <button class="delete-button" @click="shopStore.openDeleteModal(shop.shopSeq)">
              매장 삭제
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div v-show="shopStore.showModal" class="modal-overlay">
    <div class="modal-content">
      <p class="modal-text">매장을 삭제하시겠습니까?</p>
      <div class="modal-buttons">
        <button @click="shopStore.confirmDeleteShop">네</button>
        <button @click="shopStore.closeModal">아니오</button>
      </div>
    </div>
  </div>
</template>


<style scoped>
.admin-container {
  display: flex;
  flex-direction: column;
  width: 90%;
  max-width: 1200px;
  margin: 60px auto;
  padding: 20px;
  background-color: white;
  border-radius: 10px;
}

.main-header {
  text-align: center;
  margin: 100px 0 0 0;
}

.title {
  font-size: 36px;
  font-weight: bold;
}

.content-box {
  display: flex;
  padding: 20px;
  border-radius: 10px;
}

.nav {
  width: 250px;
  padding: 96px 0 0 20px;
}

.main-content {
  flex: 1;
  padding: 0 0 5px 0;
  display: flex;
  flex-direction: column;
}

.content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.shop-item {
  display: flex;
  align-items: center;
  padding: 20px;
}

.store-image {
  width: 133px;
  height: 133px;
  object-fit: cover;
  border-radius: 8px;
  margin-right: 20px;
}

.shop-info {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.shop-name {
  font-size: 30px;
  font-weight: bold;
  margin: 0 0 10px 0;
}

.shop-category {
  font-size: 20px;
  margin: 0 0 40px 0;
}

.shop-phone,
.shop-address {
  font-size: 15px;
  margin: 0px 0 1px 0;
  color: #555;
}

.delete-button {
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
}

.delete-button:hover {
  text-decoration: underline;
}

.search-box {
  display: flex;
  justify-content: center;
  margin: 40px 0 40px 0;
}

.search-box input {
  width: 764px;
  height: 30px;
  padding: 10px;
  background-color: #f0f0f0;
  text-align: center;
  border: 1px solid #f0f0f0;
  border-radius: 52px;
  font-size: 16px;
}

.search-box input:focus {
  outline: none;
  border-color: #633A02;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
  width: 543px;
  height: 204px;
}

.modal-text {
  font-size: 30px;
  margin: 35px 0 50px 0;
}

.modal-buttons {
  display: flex;
  justify-content: center;
  gap:39px;
}

.modal-buttons button {
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  width: 103px;
  height: 42px;
}

.modal-buttons button:first-child {
  background-color: #633A02;
  color: white;
}

.modal-buttons button:last-child {
  background-color: white;
  color: #633A02;
  border: 1px solid #633A02;
}
</style>
