<script setup>
import { ref, defineEmits } from "vue";
import { bookProduct } from "@/api/book/bookApi.js";
import {useUserStore} from "@/store/UserStore.js";

const userStore = useUserStore();

const props = defineProps({
  productQty: {
    type: Number,
    required: true
  },
  productSeq: {
    type: Number,
    required: true
  },
});

const emit = defineEmits();

// 수량 조절
const selectedQuantity = ref(1);
const updateQuantity = (change) => {
  const updatedQuantity = selectedQuantity.value + change;
  if (updatedQuantity < 1) {
    alert("1개 이상부터 예약 가능합니다.")
    return;
  }
  if (updatedQuantity > props.productQty) {
    return;
  }
  selectedQuantity.value = updatedQuantity;
}

const booking = async() => {
  try {
    const userSeq = userStore.userSeq;
    await bookProduct(props.productSeq, userSeq, selectedQuantity.value);
    alert("예약이 완료되었습니다.");

    // 예약 성공 시 부모에게 업데이트된 재고 수량 전달
    const updatedQty = props.productQty - selectedQuantity.value;
    emit('updated-quantity', updatedQty);

    emit('close');
  } catch(error) {
    console.error("예약 중 오류 발생:", error);
    emit('close');
  }
}
</script>

<template>
  <div class="modal-box">
    <div class="modal-content-box flex">
      <div class="text-box">
        <div class="title-text">구매하시려는 개수를 입력하세요.</div>
        <div class="available-quantity flex">현재 남은 개수: {{ productQty }}개</div>
      </div>
      <div class="flex quantity-box">
        <div>
          <img class="quantity-button-img" @click="updateQuantity(-1)" src="https://goruna.s3.us-west-1.amazonaws.com/6e63033f-4c2e-4028-9f91-f73f1790c465_minus.png" alt="수량 감소 버튼">
        </div>
        <div class="selected-quantity">{{ selectedQuantity }}</div>
        <div>
          <img class="quantity-button-img" @click="updateQuantity(1)"src="https://goruna.s3.us-west-1.amazonaws.com/8bee0745-157d-42e2-8610-745c98c30cf5_img.png" alt="수량 증가 버튼">
        </div>
      </div>
      <button class="book-button" @click="booking()">예약하기</button>
    </div>
  </div>
</template>

<style scoped>
.flex {
  display: flex;
}

.modal-box {
  width: 566px;
  height: 328px;
  background-color: var(--white-background);
  padding: 87px 0 0 0;
}

.modal-content-box {
  width: 418px;
  margin: 0 auto;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.title-text {
  font-size: 30px;
}

.available-quantity {
  justify-content: center;
  margin: 10px auto;
  font-size: 15px;
  color: var(--text-red);
}

.quantity-box {
  margin: 33px 0;
  align-items: center;
}

.quantity-button-img {
  width: 24px;
  height: 24px;
  cursor: pointer;
}

.selected-quantity {
  margin: 0 37px;
}

.book-button {
  font-family: "Gowun Dodum";
  width: 120px;
  height: 29px;
  background-color: var(--button-brown);
  border: none;
  border-radius: 10px;
  color: var(--text-white);
  cursor: pointer;
}
</style>