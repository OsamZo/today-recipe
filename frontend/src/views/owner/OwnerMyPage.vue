<script setup>
import '@/assets/css/reset.css';
import UserInfo from "@/views/user/UserInfo.vue";
import {computed, onMounted, ref} from "vue";
import ReviewPage from "@/views/review/ReviewReadPage.vue";
import OwnerBookList from "@/views/owner/OwnerBookList.vue";
import ShopInfo from "@/views/owner/ShopInfo.vue";
import OwnerProduct from "@/views/owner/OwnerProduct.vue";
import {useShopStore} from "@/store/ShopStore.js";

// 메뉴 선택 시 component 전환
const selectedMenu = ref('userInfo');
const shopStore = useShopStore();

const selectMenu = (menu) => {
  console.log(menu);
  selectedMenu.value = menu;
  if(selectedMenu.value === 'ShopInfo') {
    shopStore.loadOnwerShopData();
    const shopData = computed(() => shopStore.shopData);
  }
};

// 화면 마운트 시 개인 정보 조회 화면 로드
onMounted(() => {

});









</script>

<template>
  <div class="page">
    <div class="page-title">마이페이지</div>
    <div class="white-container">

      <div class="menu-container">
        <div class="left-menu">
          <div class="user-info">내 정보</div>
          <div
              class="menu-button"
              :class="{ 'active': selectedMenu === 'userInfo' }"
              @click="selectMenu('userInfo')">
            회원 정보 조회
          </div>
          <div
              class="menu-button"
              :class="{ 'active': selectedMenu === '예약 내역' }"
              @click="selectMenu('예약 내역')">
            예약 내역
          </div>
          <div
              class="menu-button"
              :class="{ 'active': selectedMenu === '나의 이용 내역' }"
              @click="selectMenu('나의 이용 내역')">
            나의 이용 내역
          </div>

          <br>

          <div class="user-info">내 정보</div>
          <div
              class="menu-button"
              :class="{ 'active': selectedMenu === 'OwnerBookList' }"
              @click="selectMenu('OwnerBookList')">
            예약 정보 확인
          </div>
          <div
              class="menu-button"
              :class="{ 'active': selectedMenu === 'ShopInfo' }"
              @click="selectMenu('ShopInfo')">
            매장 정보
          </div>
          <div
              class="menu-button"
              :class="{ 'active': selectedMenu === 'OwnerProduct' }"
              @click="selectMenu('OwnerProduct')">
            매장 관리
          </div>
        </div>

        <div class="menu-content">
          <UserInfo v-if="selectedMenu === 'userInfo'"/>
          <ReviewPage v-else-if="selectedMenu === 'ReviewPage'"/>
          <OwnerBookList v-else-if="selectedMenu === 'OwnerBookList'"/>
          <ShopInfo v-else-if="selectedMenu === 'ShopInfo'" :shopData="shopData"/>
          <OwnerProduct v-else-if="selectedMenu === 'OwnerProduct'"/>
          <!-- 다른 컴포넌트들 추가해서 사용하세요 -->
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.left-menu {
  margin-right: 70px;
}

.user-info {
  font-size: 30px;
  color: var(--text-black);
  margin-bottom: 15px;
}

.page {
  flex-direction: column;
  align-items: center;
  display: flex;
  width: 100%;
}

.page-title {
  font-size: 40px;
  color: var(--text-black);
  margin: 80px 0px 40px 0px;
}

.white-container {
  border-radius: 43px;
  background: #FFF;
  box-shadow: 0px 4px 13px 0px rgba(0, 0, 0, 0.13) inset;
  padding: 70px;
  width: 80%;
}

.menu-content {
  width: 100%;
}

.menu-container {
  display: flex;
}

.menu-button {
  margin-left: 45px;
  font-size: 25px;
  white-space: nowrap;
  margin-top: 10px;
}
</style>