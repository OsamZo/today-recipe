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
          <img src="/favicon.ico" alt="상품 이미지" class="product-image"/>
        </div>

        <div class="input-group-product">
          <input class="product-name" placeholder="랜덤 박스">
          <textarea class="product-desc" placeholder="상품 설명을 입력해주세요"/>
        </div>

        <div class="input-group-price">
          <button class="button-sold-out">품절</button>
          <div class="input-original-price">
            원가 : <input type="text" placeholder="원가 입력">
          </div>
          <div class="input-sale-price">
            판매가 : <input type="text" placeholder="판매가 입력">
          </div>
        </div>
      </div>

      <button class="saveImgButton">이미지 저장</button>

      <div class="set-close-time">
        마감 시간 <input class="close-time-hour" type="text" placeholder=""/> 시
        <input class="close-time-min" type="text" placeholder=""/> 분
      </div>

      <div class="set-quantity">
        판매 수량 <button class="decrement">-</button>
        <input type="text" id="quantity" value=""/>
        <button class="increment">+</button>
      </div>

    </div>


  </div>



</template>

<style scoped>
.content-container *{
  margin-bottom: 5px;
  font-family: "Gowun Dodum";
}
.page-content-title{
  display: flex;
  padding-bottom: 12px;
  border-bottom: 1px solid var(--button-brown);
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}
.menu-title{
  font-size: 30px;
}

.product-info{
  display: flex;
  justify-content: space-between;
}
.product-image{
  width: 160px;
  height: 160px;
  object-fit: cover;
  border-radius: 8px;
  margin-right: 20px;
}
.product-info input, textarea {
  border-radius: 8px;
  margin-bottom: 5px;
}
.product-name{
  color: var(--text-black);
  background-color: var(--button-brown2);
  border: none;
  width: 150px;
  height: 30px;
  margin-top: 5px;
}
.product-desc{
  display: block;
  border: 1px solid var(--button-brown);
  width: 500px;
  height: 90px;
}

.input-group-price{
  display: flex;
  flex-direction: column;
  justify-items: center;
  align-items: flex-end;
  font-size: 17px;
  margin-top: 5px;
}
.input-group-price *{
  margin-bottom: 5px;
  text-align: center;
}
.button-sold-out{
  display: flex;
  justify-content: center;
  align-items: center;
  width: 70px;
  height: 30px;
  color: var(--text-white);
  border-radius: 8px;
  background: #ff8c00;
  border: none;
  cursor: pointer;
}
.input-group-price input{
  border: 1px solid var(--button-brown);
  width: 100px;
  height: 35px;
}

.saveImgButton{
  width: 116px;
  height: 30px;
  color: var(--text-white);
  border-radius: 8px;
  background: var(--button-brown);
  display: flex;
  justify-content: center;
  align-items: center;
  border: none;
  cursor: pointer;
  margin-bottom: 10px;
}
.set-close-time{
  margin-left: 55px;
}
.set-close-time *{
  background-color: var(--button-brown2);
  border: none;
  border-radius: 8px;
  width: 70px;
  height: 30px;
  text-align: center;
}
.close-time-hour{
  margin-left: 10px;
}
.close-time-min{
  margin-left: 20px;
}

.set-quantity{
  margin-left: 55px;
  display: flex;
  align-items: center;
}
.set-quantity *{
  background-color: white;
  border: 1px solid var(--button-brown);
  margin-bottom: 0;
}
.set-quantity button{
  width: 30px;
  height: 30px;
  cursor: pointer;
}
#quantity{
  text-align: center;
  width: 45px;
  height: 26px;
}
.decrement{
  border-radius: 8px 0 0 8px;
  margin-left: 13px;
}
.increment{
  border-radius: 0 8px 8px 0;
}



</style>