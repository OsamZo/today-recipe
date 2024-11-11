<script setup>
import {computed, defineProps, onMounted, ref} from "vue";
import {deleteUser, updateUserInfo} from "@/api/user/UserApi.js";

const props = defineProps({
  modalType: {
    type: String,
    required: true
  },
  userSeq: {
    type: Number,
    required: true,
  },
});

const inputContent = ref('');
const modalTitle = computed(() =>
    props.modalType === 'leave' ? '회원 탈퇴' : '닉네임 변경'
);

const modalButtonMessage = computed(() =>
    props.modalType === 'leave'
        ? '탈퇴하기'
        : '변경'
);

const inputPlaceholder = computed(() =>
    props.modalType === 'leave'
        ? '탈퇴합니다. 를 정확히 입력하세요'
        : ''
)

const handleAction = async (userSeq) => {
  if (props.modalType === 'nickname') {
    try {
      await updateUserInfo(userSeq, inputContent.value);
      alert("닉네임이 성공적으로 변경되었습니다."); // 성공 메시지 표시
    } catch (error) {
      alert("닉네임 변경에 실패했습니다.");
    }
  } else if (props.modalType === 'leave') {
    try {
      await deleteUser(userSeq);
      // 회원 탈퇴 로직 작성 (예: deleteUserAccount API 호출)
      alert('회원 탈퇴 처리를 진행합니다.');
    } catch(error) {
      alert('회원 탈퇴를 실패했습니다.');
    }

  }
};
</script>

<template>
  <div class="content">
    <div class="title">{{ modalTitle }}</div>
    <div>
      <input class="input" v-model="inputContent" :placeholder="inputPlaceholder">
    </div>
    <button class="button" @click="handleAction(userSeq)">{{ modalButtonMessage }}</button>
  </div>
</template>

<style scoped>
.content {
  width: 601px;
  height: 356px;
  background-color: var(--white-background);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.title {
  font-size: 40px;
  margin-bottom: 32px;
}

.input {
  font-family: "Gowun Dodum";
  width: 404px;
  height: 40px;
  background-color: var(--ivory-background);
  border-radius: 10px;
  border: transparent;
  text-align: center;
  margin-bottom: 21px;
}

.button {
  font-family: "Gowun Dodum";
  width: 117px;
  height: 30px;
  color: var(--text-white);
  background-color: var(--button-brown);
  border: transparent;
  border-radius: 10px;
}
</style>