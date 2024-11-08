<script setup>
import { ref } from 'vue';
import axios from 'axios';

const nickname = ref('');
const nicknameError = ref(false);
const userSeq = 1;

const submitNickname = async () => {
  if (nicknameError.value || !nickname.value.trim()) {
    alert('닉네임을 올바르게 입력해주세요.');
    return;
  }
  console.log('닉네임 값:', nickname.value);
  try {

    console.log('닉네임 값:', nickname.value);
    const response = await axios.post(`http://localhost:8100/api/v1/user/${userSeq}/nickname`, {
      userNickname: nickname.value
    });
  } catch (e) {
    console.log('닉네임 추가 실패', e);
    alert('닉네임 추가 실패');
  }
};


const checkNicknameError = () => {
  nicknameError.value = nickname.value.trim() === '';
};
</script>


<template>
  <div class="content-box">
    <div class="login-title">닉네임 설정</div>
    <input type="text" class="input-text" v-model="nickname" @input="checkNicknameError" placeholder="닉네임을 입력하세요">
    <div v-if="nicknameError" class="error-message">닉네임에 공백만 입력할 수 없습니다.</div>

    <div class="submit-button" @click="submitNickname">확인</div>
  </div>
</template>

<style scoped>
.input-text {
  background-color: var(--button-gray);
  border-radius: 52px;
  border: none;
  width: 60%;
  height: 44px;
  margin-bottom: 10px;
  padding: 0px 30px;
  box-sizing: border-box;
}
.submit-button {
  background-color: var(--button-brown);
  border-radius: 52px;
  color: var(--text-white);
  width: 60%;
  height: 44px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.content-box {
  border-radius: 43px;
  background: #FFF;
  box-shadow: 0px 4px 13px 0px rgba(0, 0, 0, 0.13) inset;
  padding: 100px 130px;
  margin: 100px 300px;
  align-items: center;
  display: flex;
  flex-direction: column;
}
.login-title {
  font-size: 25px;
  margin-bottom: 50px;
  font-weight: bold;
}
.error-message {
  color: red;
  font-size: 12px;
  margin-top: 5px;
}
</style>
