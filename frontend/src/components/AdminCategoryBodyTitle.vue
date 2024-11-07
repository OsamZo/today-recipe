<script setup>
import { ref } from "vue";
import axios from "axios";

const showModal = ref(false);
const newCategoryName = ref("");

// 모달 열기 및 닫기
const openModal = () => {
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
  newCategoryName.value = ""; // 입력창 초기화
};

// 카테고리 추가 API 요청
const addCategory = async () => {
  if (newCategoryName.value.trim() === "") {
    alert("카테고리 이름을 입력해주세요.");
    return;
  }

  try {
    const response = await axios.post("http://localhost:8100/api/v1/admin/category", {
      categoryName: newCategoryName.value,
    });

    if (response.status === 200) {
      alert("카테고리가 추가되었습니다.");
      closeModal();
    }
  } catch (error) {
    console.error("카테고리 추가 도중 오류 발생:", error);
    alert("카테고리 추가에 실패했습니다.");
  }
};
</script>

<template>
  <div class="header">
    <div class="content">
      <p class="title">카테고리 관리</p>
      <button class="add-button" @click="openModal">카테고리 추가</button>
    </div>
    <div class="bar"></div>
  </div>

  <!-- 모달 창 -->
  <div v-if="showModal" class="modal-overlay">
    <div class="modal-content">
      <p class="modal-title">카테고리 추가</p>
      <input
          v-model="newCategoryName"
          type="text"
          class="modal-input"
          placeholder="카테고리 이름을 입력하세요"
      />
      <button class="modal-add-button" @click="addCategory">추가하기</button>
      <button class="modal-close-button" @click="closeModal">닫기</button>
    </div>
  </div>
</template>

<style scoped>
.header {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 20px;
}
.content{
  display: flex;
  margin: 0 0 0 300px;
  gap: 160px;
}

.bar{
  margin: 0 0 20px 0;
}
.title {
  font-size: 40px;
  margin: 25px 0 40px 0;
  text-align: center;
}

.add-button {
  margin: 45px 0 1px 0;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  background-color: #633A02;
  color: white;
  border: none;
  border-radius: 10px;
  width: 140px;
  height: 51px;
}

.bar {
  width: 840px;
  height: 3.54px;
  background-color: #633A02;
}

/* 모달 스타일 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  width: 300px;
}

.modal-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

.modal-input {
  width: 100%;
  padding: 10px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

.modal-add-button,
.modal-close-button {
  background: none;
  border: none;
  font-size: 16px;
  padding: 10px;
  cursor: pointer;
  border-radius: 5px;
  width: 100%;
  margin-bottom: 10px;
}

.modal-add-button {
  background-color: #633A02;
  color: white;
}

.modal-close-button {
  background-color: #ccc;
}
</style>
