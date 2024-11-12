<script setup>
import { useUserStore } from '@/store/UserStore';
import { onMounted } from 'vue';

const userStore = useUserStore();

onMounted(() => {
  if (window.location.search.includes('code')) {
    const urlParams = new URLSearchParams(window.location.search);
    const authCode = urlParams.get('code');
    if (authCode) {
      userStore.processGoogleAuth(authCode);
    }
  }
});

const initiateGoogleLogin = () => {
  userStore.handleGoogleLogin();
};
</script>

<template>
  <div class="content-box">
    <div class="login-title">SNS 계정으로 간편 로그인</div>
    <div class="gray-btn">
      <img src="https://goruna.s3.us-west-1.amazonaws.com/1a90a548-a272-4c5d-aa3f-076c6b74997d_kakaotalk.png" alt="Kakao Login">
      <div class="login-type">카카오 계정으로 로그인</div>
    </div>
    <div class="gray-btn" @click="initiateGoogleLogin">
      <img src="https://goruna.s3.us-west-1.amazonaws.com/755c83be-a394-4344-801c-6b3f8f118b0f_google.png" alt="Google Login">
      <div class="login-type">구글 계정으로 로그인</div>
    </div>
  </div>
</template>

<style scoped>
.login-type {
  margin: auto;
  font-size: 1rem;
}

.login-title {
  font-size: 2rem;
  margin-bottom: 3rem;
  font-weight: bold;
  text-align: center;
}

.content-box {
  border-radius: 43px;
  background: #FFF;
  box-shadow: 0px 4px 13px 0px rgba(0, 0, 0, 0.13) inset;
  padding: 5vw 5vh;
  margin: 5vw auto;
  align-items: center;
  display: flex;
  flex-direction: column;
  max-width: 800px;
  min-width: 300px;
  width: 80%; /* 비율 유지 */
}

.gray-btn {
  display: flex;
  background-color: var(--button-gray);
  border-radius: 52px;
  align-items: center;
  width: 100%; /* 부모 요소의 너비에 맞춰 조정 */
  max-width: 400px;
  min-width: 150px;
  height: 3rem;
  justify-content: flex-start;
  padding: 0 1rem;
  margin: 0.5rem 0;
}

.gray-btn img {
  height: 2rem;
  margin-right: 0.5rem;
}
</style>
