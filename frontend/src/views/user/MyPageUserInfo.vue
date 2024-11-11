<script setup>

import MyPageBox from "@/components/MyPageBox.vue";
import {onMounted, reactive, ref} from "vue";
import {fetchUserInfo} from "@/api/user/UserApi.js";
import {useUserStore} from "@/store/UserStore.js";
import {fetchOwnerShopInfo} from "@/api/shop/ShopReadApi.js";
import UserInfoUpdateModal from "@/components/UserInfoUpdateModal.vue";
import {useRouter} from "vue-router";

const router = useRouter();
const userStore = useUserStore();
const userInfo = reactive([]);

const userSeq = userStore.userSeq;

const loadUserInfo = async(userSeq) => {
  try {
    const data = await fetchUserInfo(userSeq);
    Object.assign(userInfo, data);
  } catch(error) {
    console.log("회원 정보를 불러오는데 실패했습니다.", error);
  }
}

const shopData = ref(null);
// 회원의 shop 데이터를 가져오는 함수
const loadShopData = async (userSeq) => {
  try {
    const response = await fetchOwnerShopInfo(userSeq);
    shopData.value = response || null; // API 응답을 shopData.value에 할당
  } catch (error) {
    console.error(error);
    shopData.value = null;
  }
};

// 모달
const isModalOpen = ref(false);
const modalType = ref('');

const openModal = (type) => {
  modalType.value = type;
  isModalOpen.value = true;
}

const closeModal = () => {
  isModalOpen.value = false;
}

const goToAddShop = () => {
  router.push("/user/shop/apply");
}

onMounted(() => {
  const userSeq = userStore.userSeq;

  if (!userSeq) {
    console.error('유효하지 않은 사용자 정보입니다.');
    return;
  }

  loadUserInfo(2);
  loadShopData(userSeq);
})
</script>

<template>
  <div v-if="isModalOpen" class="modal-background" @click="closeModal">
    <UserInfoUpdateModal
        :modalType="modalType"
        :userSeq="userSeq"
        @close="closeModal"
        @click.stop
    />
  </div>
  <MyPageBox>
    <template #action>
      <div class="user-info-box">
        <div class="flex title-box">
          <div class="content-title">회원 정보</div>
          <div class="leave" @click="openModal('leave')">회원 탈퇴</div>
        </div>
        <hr class="brown-hr">
        <div class="content-box">
          <div class="flex">
            <div class="sub-title">닉네임</div>
            <div class="text-content">{{ userInfo.userNickname }}</div>
            <button
                class="button brown-button change-button"
                @click="openModal('nickname')">변경하기</button>
          </div>
          <div class="flex">
            <div class="sub-title">이메일</div>
            <div class="text-content">{{ userInfo.userEmail }}</div>
          </div>
          <div class="flex">
            <div class="sub-title" id="register-shop-button">매장등록</div>
            <div>
              <div
                  v-if="shopData && shopData.shopApprStatus === 'PENDING'"
                  class="button brown-button pending-box flex"
              >
                처리중
              </div>
              <div
                  v-else-if="shopData && shopData.shopApprStatus === 'REJECT'"
                  class="button reject-box flex"
              >
                반려됨
              </div>
              <div
                  v-else-if="shopData && shopData.shopApprStatus === 'APPROVE'"
                  class="approve-box flex"
              >
                <div class="button shop-title-box flex">쿠키집</div>
                <button class="button brown-button">수정하기</button>
                <button class="button brown-button">삭제하기</button>
              </div>
              <button v-else class="button brown-button" @click="goToAddShop">등록하기</button>
            </div>
          </div>
        </div>
      </div>
    </template>
  </MyPageBox>
</template>

<style scoped>
.flex {
  display: flex;
  align-items: center;
}

.modal-background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
  z-index: 100;
  display: flex;
  justify-content: center;
  align-items: center;
}

.user-info-box {
  margin-bottom: 50px;
}

.title-box {
  align-items: center;
  justify-content: space-between;
}

.content-title {
  font-size: 30px;
  margin: 0 0 14px 20px;
}

.leave {
  font-size: 15px;
  color: var(--text-gray);
  margin: 0 10px 16px 0;
}

.brown-hr {
  border: 1px solid var(--button-brown);
  width: 837px;
}

.content-box {
  margin: 140px 0 0 177px;
}

.content-box > div {
  margin: 30px 0;
}

.sub-title {
  font-size: 28px;
  font-weight: bold;
  margin-right: 123px;
}

#register-shop-button {
  margin-right: 90px;
}

.text-content {
  font-size: 30px;
}

.button {
  font-family: "Gowun Dodum";
  width: 116px;
  height: 42px;
  border: transparent;
  border-radius: 10px;
  margin-right: 20px;
}

.brown-button {
  background-color: var(--button-brown);
  color: var(--white-background);
}

.change-button {
  margin-left: 30px;
}

.pending-box {
  justify-content: center;
  align-items: center;
}

.reject-box {
  justify-content: center;
  align-items: center;
  background-color: var(--orange);
  color: var(--text-white);
}

.shop-title-box {
  border: 1px solid var(--button-brown);
  color: var(--button-brown);
  justify-content: center;
  align-items: center;
}
</style>