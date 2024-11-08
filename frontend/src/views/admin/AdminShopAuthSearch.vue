<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import AdminNav from "@/components/AdminNav.vue";
import AdminBodyTitle from "@/components/AdminBodyTitle.vue";
import { useRouter } from "vue-router";

const shopList = ref([]); // storeList를 ref로 정의하여 응답 데이터를 저장
const page = 1; // 현재 페이지
const size = 10; // 페이지당 항목 수
const router = useRouter(); // Vue 라우터 인스턴스 가져오기

// 데이터 가져오는 함수 정의
const fetchShops = async () => {
  try {
    const response = await axios.get("http://localhost:8100/api/v1/admin/shop/auth", {
      params: { page, size }
    });
    shopList.value = response.data.data; // 응답 데이터로 storeList 갱신
  } catch (error) {
    console.error("데이터를 불러오는 중 오류 발생:", error);
  }
};

// "처리하기" 버튼 클릭 시 해당 경로로 이동
const handleShopAuth = (shopSeq) => {
  router.push(`/admin/shopAuth/${shopSeq}/auth`);
};

// 컴포넌트가 마운트될 때 데이터 요청
onMounted(() => {
  fetchShops();
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
          <!-- 매장 리스트 렌더링 -->
          <div class="shop-item" v-for="shop in shopList" :key="shop.shopSeq">
            <img :src="shop.shopImgUrl" alt="매장 이미지" class="store-image" />
            <div class="shop-info">
              <p class="shop-name">{{ shop.shopName }}</p>
              <p class="shop-category">{{ shop.categoryName }}</p>
              <p class="shop-phone">전화 번호 | {{ shop.shopTel }}</p>
              <p class="shop-address">매장 주소 | {{ shop.shopAddress }}</p>
            </div>
            <!-- 상태별로 버튼 스타일과 텍스트 변경 -->
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
/* 관리자 페이지 전체 스타일 */
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

/* 메인 박스 스타일 */
.content-box {
  display: flex;
  padding: 20px;
  border-radius: 10px;
}

/* 네비게이션 바 스타일 */
.nav {
  width: 250px;
  padding: 96px 0 0 20px;
}

/* 메인 콘텐츠 스타일 */
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

/* 매장 항목 스타일 */
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

/* 상태별 버튼 스타일 */
.status {
  padding: 10px 20px;
  font-size: 14px;
  border-radius: 5px;
  cursor: pointer;
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
}
</style>
