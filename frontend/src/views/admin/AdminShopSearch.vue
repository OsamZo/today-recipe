<script setup>
import AdminNav from "@/components/AdminNav.vue";
import AdminSearchBodyTitle from "@/components/AdminSearchBodyTitle.vue";
import {ref, onMounted} from "vue";
import axios from "axios";

const shopList = ref([]);
const page = 1;
const size = 10;
const searchQuery = ref("");
let allShops = ref([]);
const showModal = ref(false);  // 모달 표시 상태
const shopSeqToDelete = ref(null); // 삭제할 매장 ID

const fetchShops = async () => {
  try {
    const response = await axios.get('http://localhost:8100/api/v1/admin/shop', {
      params: { page, size }
    });
    allShops.value = response.data.data.filter(shop => shop.shopDelStatus !== 'Y');
    shopList.value = [...allShops.value];
  } catch (error) {
    console.error("데이터를 불러오는 중 오류 발생", error);
  }
};

const searchShops = async () => {
  if (!searchQuery.value) {
    shopList.value = [...allShops.value];
  } else {
    shopList.value = allShops.value.filter(shop =>
        shop.shopName.toLowerCase().includes(searchQuery.value.toLowerCase())
    );
  }
};

const openDeleteModal = (shopSeq) => {
  shopSeqToDelete.value = shopSeq;
  showModal.value = true;
};

const confirmDeleteShop = async () => {
  try {
    await axios.delete(`http://localhost:8100/api/v1/admin/shop/${shopSeqToDelete.value}`);
    fetchShops();
    showModal.value = false;
  } catch (error) {
    console.error("삭제 요청 중 오류 발생:", error.response?.data || error);
  }
};

const closeModal = () => {
  showModal.value = false;
  shopSeqToDelete.value = null;
};

onMounted(() => {
  fetchShops();
});

const handleSearchKeyPress = (event) => {
  if (event.key === "Enter") {
    searchShops();
  }
};
</script>

<template>
  <div class="main-header">
    <p class="title">관리자 페이지</p>
  </div>
  <div class="admin-container">
    <div class="content-box">
      <AdminNav class="nav"/>
      <div class="main-content">
        <AdminSearchBodyTitle/>

        <div class="search-box">
          <input
              v-model="searchQuery"
              type="text"
              placeholder="찾고있는 정보가 있나요?"
              @keydown="handleSearchKeyPress"
          />
        </div>

        <div class="content">
          <div class="shop-item" v-for="shop in shopList" :key="shop.shopSeq">
            <img :src="shop.shopImgUrl" alt="매장 이미지" class="store-image"/>
            <div class="shop-info">
              <p class="shop-name">{{ shop.shopName }}</p>
              <p class="shop-category">{{ shop.categoryName }}</p>
              <p class="shop-address">매장 주소 | {{ shop.shopAddress }}</p>
            </div>
            <button class="delete-button" @click="openDeleteModal(shop.shopSeq)">
              매장 삭제
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 모달 창 -->
  <div v-if="showModal" class="modal-overlay">
    <div class="modal-content">
      <p class="modal-text">매장을 삭제하시겠습니까?</p>
      <div class="modal-buttons">
        <button @click="confirmDeleteShop">네</button>
        <button @click="closeModal">아니오</button>
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

.shop-item {
  display: flex;
  align-items: center;
  padding: 20px;
}

.store-image {
  width: 133px;
  height: 133px;
  object-fit: cover;
  border-radius: 8px;
  margin-right: 20px;
}

.shop-info {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.shop-name {
  font-size: 30px;
  font-weight: bold;
  margin: 0 0 10px 0;
}

.shop-category {
  font-size: 20px;
  margin: 0 0 40px 0;
}

.shop-phone,
.shop-address {
  font-size: 15px;
  margin: 0px 0 1px 0;
  color: #555;
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

.search-box {
  display: flex;
  justify-content: center;
  margin: 40px 0 40px 0;
}

.search-box input {
  width: 764px;
  height: 30px;
  padding: 10px;
  background-color: #f0f0f0;
  text-align: center;
  border: 1px solid #f0f0f0;
  border-radius: 52px;
  font-size: 16px;
}

.search-box input:focus {
  outline: none;
  border-color: #633A02;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
  width: 543px;
  height: 204px;
}

.modal-text {
  font-size: 30px;
  margin: 35px 0 80px 0;
}

.modal-buttons {
  display: flex;
  justify-content: center;
  gap:39px;

}

.modal-buttons button {
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  width: 103px;
  height: 42px;
}

.modal-buttons button:first-child {
  background-color: #633A02;
  color: white;
}

.modal-buttons button:last-child {
  background-color: white;
  color: #633A02;
  border: 1px solid #633A02;
}
</style>
