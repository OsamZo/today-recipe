<script setup>
import '@/assets/css/reset.css';
</script>

<template>
  <div class="total-content">
    <div class="page-content-title">
      <div class="menu-title">메뉴 수정</div>
    </div>


    <div class="content-container">
      <div class="product-info">
        <div class="image-container">
          <img src="#" alt="상품 이미지" class="product-image">
        </div>

        <div class="input-group-product">
          <input class="product-name" type="text" placeholder="랜덤 박스">
          <textarea rows="4" placeholder="상품 설명을 입력해주세요."></textarea>
        </div>

        <div class="input-group-price">
          <div class="button-sold-out">품절</div>

          <div class="input-original-price">
            <label>원가 : </label>
            <input type="text" placeholder="원가 입력">
          </div>

          <div class="input-sale-price">
            <label>판매가 : </label>
            <input type="text" placeholder="판매가 입력">
          </div>
        </div>
      </div>

      <div class="saveImgButton">이미지 저장</div>

      <div class="set-close-time">
        <label>마감 시간</label>
        <div class="quantity">
          <input type="number" placeholder="시 입력">
          <span>:</span>
          <input type="number" placeholder="분 입력">
        </div>
      </div>

      <div class="set-quantity">
        <label>판매 수량</label>
        <div class="quantity">
          <button>-</button>
          <input type="number" value="1">
          <button>+</button>
        </div>
      </div>

      <div class="button">저장하기</div>

    </div>

  </div>
</template>

<style scoped>
.content-container{
  display: flex;
  flex-direction: column;
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

.product-image {
  width: 133px;
  height: 133px;
  object-fit: cover;
  border-radius: 8px;
  margin-right: 20px;
}
.product-info{
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.input-group-product input, textarea{
  border-radius: 8px;
}
.product-name{
  display: block;
}
.input-group-price{
  justify-items: flex-end;
}



.quantity {
  display: flex;
  align-items: center;
}
.quantity input {
  width: 50px;
  text-align: center;
  margin: 0 5px;
}
.quantity button {
  margin-left: 5px;
  padding: 5px;
}

.saveImgButton{
  margin-right: 10px;
  width: 116px;
  height: 30px;
  color: var(--text-white);
  border-radius: 10px;
  background: var(--button-brown);
  display: flex;
  justify-content: center;
  align-items: center;
}
.button-sold-out{
  width: 116px;
  height: 42px;
  color: var(--text-white);
  border-radius: 10px;
  background: #ff8c00;
  display: flex;
  justify-content: center;
  align-items: center;
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
</style>