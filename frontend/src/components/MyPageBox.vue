<script setup>
import '@/assets/css/reset.css';
import {ref} from "vue";

const selectedMenu = ref('userInfo');

const selectMenu = (menu) => {
  console.log(menu);
  selectedMenu.value = menu;
};
</script>

<template>
  <div class="page">
    <div class="page-title">마이페이지</div>
    <div class="white-container">
      <div class="menu-container">
        <div class="left-menu">
          <div class="user-info">내 정보</div>
          <div
              class="menu-button-user"
              :class="{ 'active': selectedMenu === 'userInfo' }"
              onclick="window.location.href='/user'">
            회원 정보 조회
          </div>
          <div
              class="menu-button-book"
              :class="{ 'active': selectedMenu === 'ReviewPage' }"
              onclick="window.location.href='/book'">
            예약 내역
          </div>
          <div
              class="menu-button-history"
              :class="{ 'active': selectedMenu === '' }"
              onclick="window.location.href='/history'">
            나의 이용 내역
          </div>
          <div class="menu-button-review" onclick="window.location.href='/user/review'">
            나의 리뷰 조회
          </div>
        </div>
        <slot name="action"></slot>
      </div>
    </div>
  </div>
</template>

<style scoped>
.left-menu{
  margin-right: 70px;
}
.user-info{
  font-size: 30px;
  font-weight: bold;
  color: var(--text-black);
  margin-bottom: 15px;
}
.page{
  flex-direction: column;
  align-items: center;
  display: flex;
  width: 100%;
}
.page-title{
  font-size: 40px;
  color: var(--text-black);
  margin: 80px 0px 40px 0px;
}
.white-container{
  border-radius: 43px;
  background: #FFF;
  box-shadow: 0px 4px 13px 0px rgba(0, 0, 0, 0.13) inset;
  padding: 70px;
  width: 1260px;
}

.menu-content {
  width: 100%;
}

.menu-container{
  display: flex;
}

.menu-button-user,
.menu-button-book,
.menu-button-history,
.menu-button-review{
  font-size: 28px;
  white-space: nowrap;
  margin: 20px 0 20px 45px;
  cursor: pointer;
}

</style>