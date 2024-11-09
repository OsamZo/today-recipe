<script setup>
import '@/assets/css/reset.css';

defineProps({
  shopData: {
    type: Object,
    required: true
  }
});

</script>

<template>
  <div class="total-content">
    <div class="page-content-title">
      <div class="menu-title">매장 정보</div>
    </div>

    <div class="content-container">

      <div class="inline">
        <div class="column-name">매장명</div>
        <div class="inline">
          <div class="column-content" style="margin-right: 30px">{{ shopData.shopName }}</div>
        </div>
      </div>

      <div class="inline">
        <div class="column-name">주소</div>
        <div class="column-content">{{ shopData.shopAddress}}</div>
      </div>

      <div class="inline">
        <div class="column-name">매장 소개</div>
        <div class="column-content">{{ shopData.shopIntroduction }}</div>
      </div>


      <br><br><br><br>

      <div class="inline">
        <button class="update-shopinfo" @click="">수정하기</button>
        <button class="delete-shopinfo" @click="">매장 삭제</button>
      </div>

    </div>
  </div>
</template>

<style scoped>
.content-container{
  display: flex;
  flex-direction: column;
  margin-left: 30%;
}
.update-shopinfo{
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
.delete-shopinfo{
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
.column-content{
  font-size: 30px;
}
.column-name{
  font-size: 28px;
  color: var(--text-black);
  width: 230px;
}
.inline{
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}
.total-content{
  width: 100%;
}
.page-content-title{
  display: flex;
  padding-bottom: 12px;
  border-bottom: 1px solid var(--button-brown);
  justify-content: space-between;
  align-items: center;
  margin-bottom: 130px;
}
.menu-title{
  font-size: 30px;
}

</style>