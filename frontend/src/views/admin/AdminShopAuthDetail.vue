<script setup>
import { computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { authShopDataApi } from '@/api/admin/AuthShopDataApi.js';
import { imageModalApi } from '@/api/admin/ImageModalApi.js';
import { authBtnApi } from '@/api/admin/AuthBtnApi.js'

const route = useRoute();
const shopSeq = Number(route.params.shopSeq);

const { shop, fetchShops } = authShopDataApi(shopSeq);

const { showModal, imgUrl, openImgModal, closeModal } = imageModalApi(shopSeq);

const { acceptAuth, cancelAuth } = authBtnApi(shopSeq, fetchShops);

onMounted(() => {
  fetchShops();
});

const formattedShopOpenDate = computed(() => {
  if (shop && shop.value.shopOpenDate) {
    const date = new Date(shop.value.shopOpenDate);
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const hours = date.getHours();
    const minutes = date.getMinutes().toString().padStart(2, '0');

    const formattedDate = `${year}년 ${month}월 ${day}일 ${hours}시 ${minutes}분`;

    console.log('shop.shopOpenDate:', shop.value.shopOpenDate);
    console.log('formattedDate:', formattedDate);

    return formattedDate;
  }
  return null;
});

</script>

<template>
  <div class="container">
    <div class="header">
      <div class="header-name">매장 상세 정보</div>
    </div>
    <div class="body">
      <div class="shopAuth-info">
        <div class="shop-name">
          <div class="label">사업자 등록증</div>
          <button class="call-Auth" @click="openImgModal">서류 보기</button>
        </div>

        <div class="shop-name">
          <div class="label">상호명</div>
          <div class="value" v-if="shop && shop.shopName">{{ shop.shopName }}</div>
        </div>

        <div class="shop-date">
          <div class="label">개업일</div>
          <div class="value" v-if="shop && formattedShopOpenDate">{{ formattedShopOpenDate }}</div>
        </div>

        <div class="shop-tel">
          <div class="label">매장 전화번호</div>
          <div class="value" v-if="shop && shop.shopTel">{{ shop.shopTel }}</div>
        </div>

        <div class="shop-address">
          <div class="label">매장 주소</div>
          <div class="value" v-if="shop && shop.shopAddress">{{ shop.shopAddress }}</div>
        </div>

        <div class="shop-category">
          <div class="label">사업장 종류</div>
          <div class="value" v-if="shop && shop.categoryName">{{ shop.categoryName }}</div>
        </div>

        <div class="shop-into">
          <div class="label">사업장 소개</div>
          <div class="value" v-if="shop && shop.shopIntroduction">{{ shop.shopIntroduction }}</div>
        </div>
        <div class="btn">
          <button class="modal-confirm" @click="acceptAuth"> 승인 </button>
          <button class="modal-cancel" @click="cancelAuth"> 반려 </button>
        </div>
      </div>
    </div>
  </div>

  <!-- 모달 창 -->
  <div v-if="showModal" class="modal-overlay">
    <div class="modal-content">
      <div class="img-container">
        <img :src="imgUrl" alt="shopImg" />
      </div>
      <div>
        <button @click="closeModal" class="toggle-btn">확인</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 스타일은 기존과 동일 */
.header-name {
  font-size: 40px;
  margin: 220px 0 120px 0;
  font-weight: bold;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  height: 100vh;
}

.header {
  margin-bottom: 20px;
}

.body{
  margin-left: 80px;
}

.shopAuth-info {
  max-width: 600px;
  width: 100%;
}

.shopAuth-info > div {
  display: flex;
  justify-content: flex-start; /* 왼쪽 정렬 */
  align-items: center;
  padding: 10px 0;
  margin: 15px;
}

.shopAuth-info .label {
  font-size: 24px;
  font-weight: bold;
  width: 150px;
  text-align: left;
  margin-right: 10px;
  flex: 3;
}

.shopAuth-info .value {
  font-size: 24px;
  text-align: left;
  flex: 3;
}


.call-Auth{
  background-color: #633A02;
  color: #f0f0f0;
  border-radius: 5px;
  width: 113px;
  height: 38px;
  cursor: pointer;
  font-size: 17px;
  margin: 0 150px 0 0;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
}

.modal-content .img-container {
  width: 100%;
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.modal-content img {
  max-width: 100%;
  height: auto;
}

.toggle-btn{
  background-color: #633A02;
  color: #f0f0f0;
  border-radius: 5px;
  width: 113px;
  height: 38px;
  cursor: pointer;
  font-size: 17px;
}

.btn{
  display: flex;
  gap: 5px;
}

.modal-confirm{
  background-color: #633A02;
  color: #f0f0f0;
  border-radius: 5px;
  width: 167px;
  height: 38px;
  cursor: pointer;
  font-size: 17px;
  margin: 120px 0 0 70px;
}

.modal-cancel{
  background-color: #f0f0f0;
  color: #633A02;
  border-radius: 5px;
  border-color:#f0f0f0;
  width: 167px;
  height: 38px;
  cursor: pointer;
  font-size: 17px;
  margin: 120px 0 0 0;
}
</style>
