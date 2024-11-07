<script setup>
import '@/assets/css/reset.css';
</script>

<template>
<div class="content-box">
  <div class="login-title">SNS 계정으로 간편 로그인</div>
  <div class="gray-btn">
    <img src="https://goruna.s3.us-west-1.amazonaws.com/1a90a548-a272-4c5d-aa3f-076c6b74997d_kakaotalk.png">
    <div class="login-type">카카오 계정으로 로그인</div>
  </div>
  <div class="gray-btn">
    <img src="https://goruna.s3.us-west-1.amazonaws.com/76b82697-0da3-4672-9c85-792c86563973_Naver.png">
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