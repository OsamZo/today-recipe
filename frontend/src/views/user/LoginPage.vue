<script setup>
import '@/assets/css/reset.css';

import { ref } from 'vue';
import axios from 'axios';

const googleResult = ref(null);

const loginWithGoogle = async () => {
  try {
    const response = await axios.post('http://localhost:8100/api/v1/oauth2/google');

    window.location.href = response.data;
  } catch (error) {
    console.error('로그인 URL 요청 실패:', error);
  }
};

const resultWithGoogle = async (authCode) => {
  try {
    const response = await axios.get(`http://localhost:8100/api/v1/oauth2/google?code=${authCode}`);

    const result = response.data;

    if (result === 'signup') {
      window.location.href = '/signup';
    } else if (result === 'login') {
      window.location.href = '/';
    }
  } catch (error) {
    console.error('로그인 처리 중 오류 발생', error);
  }
};

if (window.location.search.includes('code')) {
  const urlParams = new URLSearchParams(window.location.search);
  const authCode = urlParams.get('code');
  if (authCode) {
    resultWithGoogle(authCode);
  }
}
</script>

<template>
  <div class="content-box">
    <div class="login-title">SNS 계정으로 간편 로그인</div>
    <div class="gray-btn">
      <img src="https://goruna.s3.us-west-1.amazonaws.com/1a90a548-a272-4c5d-aa3f-076c6b74997d_kakaotalk.png">
      <div class="login-type">카카오 계정으로 로그인</div>
    </div>
    <div class="gray-btn" @click="loginWithGoogle">
      <img src="https://goruna.s3.us-west-1.amazonaws.com/755c83be-a394-4344-801c-6b3f8f118b0f_google.png">
      <div class="login-type">구글 계정으로 로그인</div>
    </div>
  </div>
</template>

<style scoped>
.login-type{
  margin: auto;
}
.login-title{
  font-size: 25px;
  margin-bottom: 50px;
  font-weight: bold;
}
.content-box{
  border-radius: 43px;
  background: #FFF;
  box-shadow: 0px 4px 13px 0px rgba(0, 0, 0, 0.13) inset;
  padding: 100px 130px;
  margin: 100px 300px;
  align-items: center;
  display: flex;
  flex-direction: column;
}
.gray-btn{
  display: flex;
  background-color: var(--button-gray);
  border-radius: 52px;
  align-items: center;
  width: 40%;
  height: 44px;
  justify-content: flex-start;
  padding: 0px 20px;
  margin: 5px 0px;
}
</style>
