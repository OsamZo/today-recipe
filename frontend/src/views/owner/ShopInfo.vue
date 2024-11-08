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
        <div class="columeName">매장명</div>
        <div class="inline">
          <div class="columeContent" style="margin-right: 30px">쿠키집</div>
        </div>
      </div>

      <div class="inline">
        <div class="columeName">주소</div>
        <div class="columeContent">서울특별시 강남구 도곡동 123-12 1층</div>
      </div>

      <div class="inline">
        <div class="columeName">매장 소개</div>
        <div class="columeContent">쿠키 맛집!</div>
      </div>


      <br><br><br><br>

      <div class="inline">
        <div class="columeName">판매 정보 수정 </div>
        <div class="button">수정하기</div>
        <div class="button2">매장 삭제</div>
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
.button{
  margin-right: 10px;
  width: 116px;
  height: 42px;
  color: var(--text-white);
  border-radius: 10px;
  background: var(--button-brown);
  display: flex;
  justify-content: center;
  align-items: center;
}
.button2{
  margin-right: 10px;
  width: 116px;
  height: 42px;
  color: var(--text-black);
  border-radius: 10px;
  background: var(--button-brown2);
  display: flex;
  justify-content: center;
  align-items: center;
}
.columeContent{
  font-size: 30px;
}
.columeName{
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