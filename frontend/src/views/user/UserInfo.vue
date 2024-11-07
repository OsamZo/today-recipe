<script setup>
import '@/assets/css/reset.css';
</script>

<template>
  <div class="total-content">
    <div class="page-content-title">
      <div class="menu-title">회원 정보</div>
      <div class="user-delete">회원 탈퇴</div>
    </div>

    <div class="content-container">

      <div class="inline">
        <div class="columeName">닉네임</div>
        <div class="inline">
          <div class="columeContent" style="margin-right: 30px">미니주</div>
          <div class="button">변경하기</div>
        </div>
      </div>

      <div class="inline">
        <div class="columeName">이메일</div>
        <div class="columeContent">minju@gmail.com</div>
      </div>
      <br><br><br><br>

      <div class="inline">
        <div class="columeName">매장 등록</div>
        <div class="button">등록하기</div>
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
  width: 116px;
  height: 42px;
  color: var(--text-white);
  border-radius: 10px;
  background: var(--button-brown);
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
.user-delete{
  color: var(--text-gray);
  font-size: 15px;
}
</style>