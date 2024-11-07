<script setup>
import '@/assets/css/reset.css';
</script>

<template>
<div class="content-box">
    <div class="login-title">닉네임 설정</div>

    <input type="text" class="input-text">
    <div class="submit-button">확인</div>
</div>
</template>

<style scoped>
.input-text{
  background-color: var(--button-gray);
  border-radius: 52px;
  border: none;
  width: 60%;
  height: 44px;
  margin-bottom: 10px;
  padding: 0px 30px;
  box-sizing: border-box;
}
.submit-button{
  background-color: var(--button-brown);
  border-radius: 52px;
  color: var(--text-white);
  width: 60%;
  height: 44px;
  display : flex;
  justify-content : center;
  align-items : center;
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
.login-title{
    font-size: 25px;
    margin-bottom: 50px;
    font-weight: bold;
}
</style>