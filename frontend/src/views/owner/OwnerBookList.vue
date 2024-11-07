<script setup>
import '@/assets/css/reset.css';
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome';
</script>

<template>
<div class="total-content">
  <div class="page-content-title">
    <div class="menu-title">예약 정보 확인</div>
  </div>
<!-- 날짜별 리스트  시작 -->
<div class="padding">
  <div class="inline-date">
    <div><font-awesome-icon :icon="['far', 'calendar']" /></div>
    <div>2024-10-10</div>
  </div>
    <div class="inline-list">
      <div>쿠키네</div>
      <div>랜덤쿠키 X2</div>
      <div>6,000원</div>
      <div class="check-button">수령</div>
    </div>
    <div class="inline-list">
      <div>쿠키네</div>
      <div>랜덤쿠키 X2</div>
      <div>6,000원</div>
      <div class="check-button-red">미수령</div>
    </div>
  </div>
<!-- 날짜별 리스트  끝 -->
</div>

</template>


<style scoped>
.padding{
  padding: 0px 50px;
}
.inline-list div{
  font-size: 20px;
}
.check-button-red{
  border: 1px var(--text-red) solid;
  height: 33px;
  border-radius: 10px;
  text-align: center;
  justify-content: center;
  display: flex;
  align-items: center;
  width: 80px;
}
.check-button{
  border: 1px var(--button-brown) solid;
  border-radius: 10px;
  height: 33px;
  text-align: center;
  justify-content: center;
  display: flex;
  align-items: center;
  width: 80px;
}
.inline-list{
  display: flex;
  align-items: center;
  justify-content: space-around;
  padding: 0px 50px;
  margin: 8px 0px;
}
.inline-date{
  display: flex;
  align-items: center;
  font-size: 30px;
  color: var(--button-brown);
  margin-bottom: 20px;
}
.inline-date div{
  margin: 0px 5px;
}
.total-content{
  width: 100%;
}
.page-content-title{
  display: flex;
  padding-bottom: 12px;
  border-bottom: 1px solid var(--button-brown);
  align-items: center;
  margin-bottom: 130px
}
.menu-title{
  font-size: 30px;
}
</style>