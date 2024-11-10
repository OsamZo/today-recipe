<script setup>
import '@/assets/css/reset.css';
import {ref, watch} from "vue";

defineProps({
  shopData: {
    type: Object,
    required: true
  }
});

const emit = defineEmits(['canceled-update', 'update-shopInfo']);

const goBack = () => {
  emit('canceled-update');
}

const inputShopData = ref({
  shopTel: '',
  shopAddress: '',
  shopIntroduction: ''
});

const forwardShopData = () => {
  // console.log('UpdateShop'+ inputShopData.value.shopTel);
  emit('update-shopInfo', inputShopData.value);
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
      <div class="column-name">매장 전화번호 : </div>
      <input
          class="input-shopInfo"
          v-model="inputShopData.shopTel"
          :placeholder="shopData.shopTel"/>
    </div>

    <div class="inline">
      <div class="column-name">주소 : </div>
      <input
          class="input-shopInfo"
          v-model="inputShopData.shopAddress"
          :placeholder="shopData.shopAddress"/>
    </div>

    <div class="inline">
      <div class="column-name">매장 소개 : </div>
      <input
          class="input-shopInfo"
          v-model="inputShopData.shopIntroduction"
          :placeholder="shopData.shopIntroduction"/>
    </div>

    <br><br><br><br>

    <div class="inline">
      <button class="update-shopInfo" @click="forwardShopData">저장</button>
      <button class="cancel-shopInfo" @click="goBack">취소</button>
    </div>
  </div>
</template>

<style scoped>
.content-container {
  display: flex;
  flex-direction: column;
  margin-left: 30%;
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
.cancel-shopInfo {
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
.input-shopInfo{
  border-radius: 8px;
  margin-bottom: 5px;
  display: block;
  border: 1px solid var(--button-brown);
  width: 350px;
  height: 40px;
  font-family: "Gowun Dodum";
}
</style>