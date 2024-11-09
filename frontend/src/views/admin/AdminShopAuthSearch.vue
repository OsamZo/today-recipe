<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { shopAuthApi } from "@/api/Admin/ShopAuthApi.js";  // 새로운 파일로부터 가져오기
import AdminBodyTitle from "@/components/AdminBodyTitle.vue"
import AdminNav from "@/components/AdminNav.vue"


const { shopList, fetchShops, handleShopAuth } = shopAuthApi();
const router = useRouter();

onMounted(() => {
  const page = 1;
  const size = 10;
  fetchShops(page, size);
});
</script>

<template>
  <div class="main-header">
    <p class="title">관리자 페이지</p>
  </div>
  <div class="admin-container">
    <div class="content-box">
      <AdminNav class="nav" />
      <div class="main-content">
        <AdminBodyTitle />
        <div class="content">
          <div class="shop-item" v-for="shop in shopList" :key="shop.shopSeq">
            <img :src="shop.shopImgUrl" alt="매장 이미지" class="store-image" />
            <div class="shop-info">
              <p class="shop-name">{{ shop.shopName }}</p>
              <p class="shop-category">{{ shop.categoryName }}</p>
              <p class="shop-phone">전화 번호 | {{ shop.shopTel }}</p>
              <p class="shop-address">매장 주소 | {{ shop.shopAddress }}</p>
            </div>
            <button
                class="status"
                :class="{
                'approve': shop.shopApprStatus === 'APPROVE',
                'reject': shop.shopApprStatus === 'REJECT',
                'pending': shop.shopApprStatus === 'PENDING'
              }"
                @click="shop.shopApprStatus === 'PENDING' && handleShopAuth(shop.shopSeq)"
            >
              {{ shop.shopApprStatus === 'APPROVE' ? '처리 완료' : shop.shopApprStatus === 'REJECT' ? '반려 처리' : '처리 하기' }}
            </button>
          </div>
        </div>
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
  margin: 0 0 4px 0;
}

.shop-category {
  font-size: 20px;
  margin: 0 0 11.6px 0;
}

.shop-phone,
.shop-address {
  font-size: 15px;
  margin: 0px 0 1px 0;
  color: #555;
}

.status {
  padding: 10px 20px;
  font-size: 14px;
  border-radius: 5px;
  border: none;
}

.status.approve {
  color: #999999;
  background-color: transparent;
  border: none;
}

.status.reject {
  color: #FA9912;
  background-color: transparent;
  border: none;
}

.status.pending {
  color: white;
  background-color: #633A02;
  cursor: pointer;
}
</style>
