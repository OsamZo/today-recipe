<script setup>
import '@/assets/css/reset.css';
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
          <div class="column-content" style="margin-right: 30px">쿠키집</div>
        </div>
      </div>

      <div class="inline">
        <div class="column-name">주소</div>
        <div class="column-content">서울특별시 강남구 도곡동 123-12 1층</div>
      </div>

      <div class="inline">
        <div class="column-name">매장 소개</div>
        <div class="column-content">쿠키 맛집!</div>
      </div>


      <br><br><br><br>

      <div class="inline">
        <div class="column-name">판매 정보 수정 </div>
        <button class="update-shopinfo">수정하기</button>
        <button class="delete-shopinfo">매장 삭제</button>
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