<script setup>
import '@/assets/css/reset.css';

defineProps({
  shopData: {
    type: Object,
    required: true
  }
});

const emit = defineEmits(['update-product']);

const updateProduct = () => {
  emit('update-product');
}
const deleteShop = () => {
  emit('delete-shop');
}

</script>

<template>
  <div class="content-container">

    <div class="inline">
      <div class="column-name">매장명</div>
      <div class="inline">
        <div class="column-content">{{ shopData.shopName }}</div>
      </div>
    </div>

    <div class="inline">
      <div class="column-name">매장 전화번호</div>
      <div class="column-content">{{ shopData.shopTel }}</div>
    </div>

    <div class="inline">
      <div class="column-name">주소</div>
      <div class="column-content">{{ shopData.shopAddress }}</div>
    </div>

    <div class="inline">
      <div class="column-name">매장 소개</div>
      <div class="column-content">{{ shopData.shopIntroduction }}</div>
    </div>

    <br><br><br><br>

    <div class="inline">
      <div class="column-name">판매 정보 수정 </div>
      <button class="update-shopInfo" @click="updateProduct">수정하기</button>
      <button class="delete-shopInfo" @click="deleteShop">매장 삭제</button>
    </div>
  </div>
</template>

<style scoped>
.content-container {
  display: flex;
  flex-direction: column;
  margin-left: 30%;
  font-family: "Gowun Dodum";
}
.column-content {
  font-size: 30px;
}
.column-name {
  font-size: 28px;
  color: var(--text-black);
  width: 230px;
}
.inline {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}
.update-shopInfo {
  margin-right: 10px;
  width: 116px;
  height: 42px;
  color: var(--text-white);
  border-radius: 10px;
  background: var(--button-brown);
  display: flex;
  justify-content: center;
  align-items: center;
  border: none;
  cursor: pointer;
}
.delete-shopInfo {
  margin-right: 10px;
  width: 116px;
  height: 42px;
  color: var(--text-black);
  border-radius: 10px;
  background: var(--button-brown2);
  display: flex;
  justify-content: center;
  align-items: center;
  border: none;
  cursor: pointer;
}
</style>