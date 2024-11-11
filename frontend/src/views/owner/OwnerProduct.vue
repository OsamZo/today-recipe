<script setup>
import { ref } from 'vue';
import axios from 'axios';
import '@/assets/css/reset.css';

// Data References
const productName = ref('');
const productDescription = ref('');
const productOriginalPrice = ref('');
const productSalePrice = ref('');
const closeTimeHour = ref('');
const closeTimeMin = ref('');
const quantity = ref(1);  // 기본 수량을 1로 설정
const productImgUrl = ref(null);

// 수량 증가/감소 함수
const incrementQuantity = () => {
  quantity.value++;
};
const decrementQuantity = () => {
  if (quantity.value > 1) quantity.value--;
};

// 이미지 파일 선택 이벤트 핸들러
const handleImageUpload = (event) => {
  productImgUrl.value = event.target.files[0];
};

// 저장하기 버튼 클릭 이벤트
const saveProduct = async () => {
  // 가격 검증
  const originalPrice = parseInt(productOriginalPrice.value, 10);
  const salePrice = parseInt(productSalePrice.value, 10);
  if (isNaN(originalPrice) || isNaN(salePrice) || originalPrice <= 0 || salePrice <= 0) {
    alert('유효한 가격을 입력해주세요.');
    return;
  }

  // 요청 데이터 설정
  const productData = {
    productName: productName.value,
    productDescription: productDescription.value,
    productOriginalPrice: originalPrice,
    productSalePrice: salePrice,
    productQty: quantity.value,
    productClosedAt: `${new Date().getFullYear()}-${new Date().getMonth() + 1}-${new Date().getDate()}T${closeTimeHour.value}:${closeTimeMin.value}:00`,
  };

  const formData = new FormData();
  formData.append('createProductReqDTO', new Blob([JSON.stringify(productData)], { type: 'application/json' }));
  formData.append('productImgUrl', productImgUrl.value);

  const userSeq = localStorage.getItem("userSeq");
  try {
    const response = await axios.post(`http://localhost:8100/api/v1/owner/product/${userSeq}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    console.log('Product saved:', response.data);
    alert('상품이 성공적으로 저장되었습니다.');
  } catch (error) {
    console.error('Error saving product:', error.response || error.message);
  }
};
</script>

<template>
  <div class="total-content">
    <div class="page-content-title">
      <div class="menu-title">메뉴 수정</div>
    </div>

    <div class="content-container">
      <div class="product-info">
        <div class="image-container">
        </div>

        <!-- 상품명과 상품 설명을 가로로 배치 -->
        <div class="input-group-product">
          <div class="input-btn">
            <input type="file" @change="handleImageUpload" style="display:none" ref="fileInput">
            <button class="saveImgButton" @click="$refs.fileInput.click()">이미지 업로드</button>
          </div>
          <div class="input-product-name">
            <div class="productN">
              상품명
            </div>
            <input class="product-name" v-model="productName" placeholder="상품명">
          </div>
          <div class="input-product-desc">
            <div class="productN">
              상품 설명
            </div>
            <textarea class="product-desc" v-model="productDescription" placeholder="상품 설명을 입력해주세요"></textarea>
          </div>
        </div>

        <div class="input-group-price">
          <div class="input-original-price">
            <div class="originalP">
              원가
            </div>
            <input type="text" v-model="productOriginalPrice" placeholder="원가 입력">

          </div>
          <div class="input-sale-price">
            <div class="priceP">
            판매가
            </div>
            <input type="text" v-model="productSalePrice" placeholder="판매가 입력">
          </div>
        </div>
      </div>

      <!-- 마감시간과 판매수량을 한 줄로 배치 -->
      <div class="set-time-quantity">
        <div class="set-close-time">
          마감 시간
          <input class="close-time-hour" type="text" v-model="closeTimeHour" placeholder=""/> 시
          <input class="close-time-min" type="text" v-model="closeTimeMin" placeholder=""/> 분
        </div>

        <div class="set-quantity">
          판매 수량
          <button class="decrement" @click="decrementQuantity">-</button>
          <input type="text" id="quantity" v-model="quantity" readonly />
          <button class="increment" @click="incrementQuantity">+</button>
        </div>
      </div>

      <div class="footer">
        <button class="save-product" @click="saveProduct">저장하기</button>
      </div>
    </div>
  </div>
</template>


<style scoped>
.content-container * {
  margin-bottom: 5px;
  font-family: "Gowun Dodum";
}
.page-content-title {
  display: flex;
  padding-bottom: 12px;
  border-bottom: 1px solid var(--button-brown);
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}
.menu-title {
  font-size: 30px;
}

.product-info {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.product-image {
  width: 160px;
  height: 160px;
  object-fit: cover;
  border-radius: 8px;
  margin-right: 20px;
}
.product-info input, textarea {
  border-radius: 8px;
  margin-bottom: 5px;
  width: 100%;
}
.product-name {
  color: var(--text-black);
  background-color: var(--button-brown2);
  border: none;
  width: 100%;
  height: 40px;
  padding: 0 10px;
  text-align: center;
  font-size: 16px;
}

.product-desc {
  margin-top: 5px;
  border: 1px solid var(--button-brown);
  width: 100%;
  height: 90px;
  resize: none;
  overflow-wrap: break-word;
  border-radius: 8px;
  padding: 10px;
}

.input-group-price {
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* 왼쪽 정렬 */
  font-size: 17px;
  margin-top: 15px;
}
.input-group-price input {
  border: 1px solid var(--button-brown);
  width: 150px;
  height: 35px;
  padding: 5px;
  margin-bottom: 10px;
}

.saveImgButton {
  width: 200px;
  height: 40px;
  color: var(--text-white);
  border-radius: 8px;
  background: var(--button-brown);
  display: flex;
  justify-content: center;
  align-items: center;
  border: none;
  cursor: pointer;
  margin-bottom: 20px;
}
.productN{
  height: 20px;
  width: 80px;
}

/* 마감시간과 판매수량을 가로로 배치 */
.set-time-quantity {
  display: flex;
  gap:40px;
  margin-top: 20px;
  width: 100%;
}

.set-close-time, .set-quantity {
  display: flex;
  align-items: center;
}

.set-close-time * {
  background-color: var(--button-brown2);
  border: none;
  border-radius: 8px;
  width: 70px;
  height: 30px;
  text-align: center;
}
.input-original-price,
.input-sale-price{
  display: flex;
  gap:20px;
}
.originalP{
  margin: 0 35px 0 0;
}
.priceP{
  margin: 0 18px 0 0;
}
.originalP,
.priceP{
  padding: 10px 0 0 0;
}

.set-quantity {
  align-items: center;
  justify-content: center;
}
.input-btn{
  margin: 0 0 0 87px;
}
.set-quantity * {
  background-color: white;
  border: 1px solid var(--button-brown);
  margin-bottom: 0;
}
.set-quantity button {
  width: 40px;
  height: 40px;
  cursor: pointer;
}
#quantity {
  text-align: center;
  width: 50px;
  height: 40px;
}

.decrement {
  border-radius: 8px 0 0 8px;
  margin: 0 0 0 10px;
  height: 10px;
}
.increment {
  border-radius: 0 8px 8px 0;
}

.save-product {
  width: 160px;
  height: 45px;
  color: var(--text-white);
  border-radius: 10px;
  background: var(--button-brown);
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}

.footer {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}
.input-group-product {
  display: flex;
  flex-direction: column; /* 세로 정렬로 변경 */
  width: 100%;
  margin-bottom: 10px;
  justify-content: flex-start;
}

.input-product-name,
.input-product-desc {
  display: flex;
  flex-direction: row; /* 가로 정렬 */
  align-items: center; /* 세로로 중앙 정렬 */
  margin-bottom: 10px;
}

.input-product-name input,
.input-product-desc textarea {
  margin-left: 10px; /* 텍스트와 인풋 박스 사이에 간격 추가 */
}

.product-name {
  width: 100%;
  height: 40px;
  padding: 0 10px;
  text-align: center;
  font-size: 16px;
}
.close-time-hour,
.close-time-min{
  margin: 0 5px 0 10px;
  width: 80px;
  height: 34px;
}
.product-desc {
  width: 100%;
  height: 90px;
  resize: none;
  overflow-wrap: break-word;
  border-radius: 8px;
  padding: 10px;
}

</style>

