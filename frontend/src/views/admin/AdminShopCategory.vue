<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import AdminNav from "@/components/AdminNav.vue";
import AdminCategoryBodyTitle from "@/components/AdminCategoryBodyTitle.vue";

const categoryList = ref([]);
const showModal = ref(false);
const selectedCategory = ref(null);

const fetchCategory = async () => {
  try {
    const response = await axios.get('http://localhost:8100/api/v1/admin/category');
    categoryList.value = response.data.data;
  } catch (error) {
    console.error("데이터를 불러오는 중 오류 발생", error);
  }
}

const confirmDeleteCategory = (category) => {
  selectedCategory.value = category;
  showModal.value = true;
};

const deleteCategory = async () => {
  try {
    const response = await axios.delete(`http://localhost:8100/api/v1/admin/category/${selectedCategory.value.categorySeq}`);
    if (response.status === 200) {
      alert(`${selectedCategory.value.categoryName}가 삭제되었습니다.`);
      fetchCategory();
      closeModal();
    }
  } catch (error) {
    alert("삭제 요청이 실패하였습니다.");
    console.error("카테고리 삭제 도중 오류 발생:", error.response?.data || error);
  }
};

const closeModal = () => {
  showModal.value = false;
  selectedCategory.value = null;
};

onMounted(() => {
  fetchCategory();
});
</script>

<template>
  <div class="main-header">
    <p class="title">관리자 페이지</p>
  </div>
  <div class="admin-container">
    <div class="content-box">
      <AdminNav class="nav" />
      <div class="main-content">
        <AdminCategoryBodyTitle class="topBar"/>
        <div class="content">
          <!-- 카테고리 리스트 렌더링 -->
          <div v-for="(category, index) in categoryList" :key="category.categorySeq">
            <div class="category-item">
              <p class="category-number">{{ index + 1 }}</p>
              <p class="category-name">{{ category.categoryName }}</p>
              <button class="delete-button" @click="confirmDeleteCategory(category)">삭제</button>
            </div>
            <div class="category-bar" />
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 삭제 확인 모달 -->
  <div v-if="showModal" class="modal-overlay">
    <div class="modal-content">
      <p>{{ selectedCategory?.categoryName }}를 삭제하시겠습니까?</p>
      <div class="modal-buttons">
        <button class="modal-confirm" @click="deleteCategory">네</button>
        <button class="modal-cancel" @click="closeModal">아니요</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.admin-container {
  display: flex;
  flex-direction: column;
  width: 90%;
  max-width: 1200px;
  margin: 60px auto;
  padding: 20px;
  background-color: white;
  border-radius: 10px;
}

.main-header {
  text-align: center;
  margin: 100px 0 0 0;
}

.title {
  font-size: 36px;
  font-weight: bold;
}

.content-box {
  display: flex;
  padding: 20px;
  border-radius: 10px;
}

.nav {
  width: 250px;
  padding: 96px 0 0 20px;
}

.main-content {
  flex: 1;
  padding: 0 0 5px 0;
  display: flex;
  flex-direction: column;
}


.content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 카테고리 항목 스타일 */
.category-item {
  display: flex;
  align-items: center;
  width: 80%;
  padding: 10px 0;
  justify-content: space-between;
  margin-left: 95px;

}

.category-number {
  font-size: 20px;
  margin-right: 10px;
}

.category-name {
  font-size: 20px;
}

.delete-button {
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
}

.delete-button:hover {
  text-decoration: underline;
}

/* 카테고리 구분선 스타일 */
.category-bar {
  width: 95%;
  height: 3px;
  background: #D9D9D9;
  border-radius: 10px;
  margin: 15px 0 0 23px;
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

.modal-buttons {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

.modal-confirm,
.modal-cancel {
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
}

.modal-confirm:hover,
.modal-cancel:hover {
  text-decoration: underline;
}
</style>

