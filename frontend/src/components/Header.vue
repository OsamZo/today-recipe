<script setup>
import {RouterLink, useRouter} from 'vue-router';
import { useUserStore } from '@/store/UserStore';

const router = useRouter();
const userStore = useUserStore();

const handleLogout = () => {
  userStore.logout(); // 로그아웃 메서드 호출
  console.log('Logout triggered'); // 로그아웃 이벤트가 호출되는지 확인하기 위해 로그 추가
};

</script>

<template>
  <header class="header">
    <div class="flex content_box">
      <RouterLink to="/">
        <div class="text_box flex">
          <div class="title">고루나 :</div>
          <div class="sub_title"> 고루고루 나누어 주다.</div>
        </div>
      </RouterLink>
      <div class="flex icon_boxes">
        <div>
          <svg width="26" height="35" viewBox="0 0 26 35" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path
                d="M2.20656 34.7374C1.28505 35.3951 0 34.7418 0 33.6157V5.98771C0 2.68078 2.70259 0 6.03642 0H19.9636C23.2974 0 26 2.68078 26 5.98771V33.6157C26 34.7418 24.7149 35.3951 23.7935 34.7374L13 27.0346L2.20656 34.7374Z"
                fill="white"/>
          </svg>
        </div>
        <div>
          <svg width="28" height="35" viewBox="0 0 28 35" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path
                d="M26.2933 28.7565L26.2933 28.7565C23.8313 32.2016 19.7703 34 13.9964 34C8.22094 34 4.16306 32.2009 1.70771 28.7548C1.24738 28.1088 1 27.3353 1 26.542V24.9318C1 23.3101 2.31458 21.9956 3.93623 21.9956H24.0678C25.6896 21.9956 27.0041 23.3101 27.0041 24.9318V26.5393C27.0041 27.3345 26.7556 28.1096 26.2933 28.7565ZM13.9964 1C18.2774 1 21.7479 4.4705 21.7479 8.75157C21.7479 13.0326 18.2774 16.5032 13.9964 16.5032C9.71523 16.5032 6.24473 13.0326 6.24473 8.75157C6.24473 4.47051 9.71522 1 13.9964 1Z"
                stroke="white" stroke-width="2"/>
          </svg>
        </div>
        <div @click="handleLogout" style="cursor: pointer;">
          <svg width="26" height="35" viewBox="0 0 26 35" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M21.6667 8.66665V4.99998C21.6667 4.02752 21.2804 3.09489 20.5927 2.40725C19.9051 1.71962 18.9725 1.33331 18 1.33331H5.16667C4.19421 1.33331 3.26157 1.71962 2.57394 2.40725C1.88631 3.09489 1.5 4.02752 1.5 4.99998V27C1.5 27.9724 1.88631 28.9051 2.57394 29.5927C3.26157 30.2803 4.19421 30.6666 5.16667 30.6666H18C18.9725 30.6666 19.9051 30.2803 20.5927 29.5927C21.2804 28.9051 21.6667 27.9724 21.6667 27V23.3333" stroke="white" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M12.5 16H34.5M34.5 16L29 10.5M34.5 16L29 21.5" stroke="white" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
      </div>
    </div>
  </header>
</template>

<style scoped>
.flex {
  display: flex;
}

.header {
  height: 115px;
  background-color: #633A02;
  color: #FFFFFF;
}

.content_box {
  padding: 39px 45px 39px 92px;
  justify-content: space-between;
}

.text_box {
  align-items: baseline;
  cursor: pointer;
  white-space: nowrap;
}

.title {
  font-size: 30px;
  margin-right: 7px;
}

.sub_title {
  font-size: 15px;
}

.icon_boxes > div {
  margin: 0 10px;
}
</style>
