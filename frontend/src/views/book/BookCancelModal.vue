<script setup>
import {cancelBookingProduct} from "@/api/book/BookApi.js";

const props = defineProps({
  bookSeq: {
    type: Number,
    required: true
  }
})

const emit = defineEmits();

const cancelBooking = async() => {
  try {
    await cancelBookingProduct(props.bookSeq);

    alert("예약이 취소되었습니다.");
    emit('bookingCancelled', props.bookSeq);

  } catch(error) {
    console.error("예약 취소 도중 오류 발생", error);
  } finally {
    emit('close');
  }
}

const closeModal = () => {
  emit('close');
}
</script>

<template>
  <div class="background">
    <div class="content-box flex">
      <div class="cancel-title">예약을 취소하시겠습니까?</div>
      <div class="flex">
        <button
            class="button confirm-button"
            @click="cancelBooking()"
        >네</button>
        <button class="button reject-button" @click="closeModal">아니요</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.flex {
  display: flex;
}

.background {
  width: 543px;
  height: 220px;
  background-color: var(--white-background);

}

.content-box {
  padding-top: 80px;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.cancel-title {
  font-size: 30px;
}

.button {
  font-family: "Gowun Dodum";
  width: 103px;
  height: 42px;
  border: none;
  border-radius: 10px;
  font-size: 15px;
  cursor: pointer;
}

.confirm-button {
  background-color: var(--white-background);
  color: var(--button-brown);
  border: 1px solid var(--button-brown);
  margin: 15px;
}

.reject-button {
  color: var(--white-background);
  background-color: var(--button-brown);
  margin: 15px;
}
</style>