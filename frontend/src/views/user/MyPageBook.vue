<script setup>
import MyPageBox from "@/components/MyPageBox.vue";
import {fetchBookList} from "@/api/user/UserApi.js";
import {nextTick, onMounted, reactive, ref} from "vue";
import {useUserStore} from "@/store/UserStore.js";
import BookCancelModal from "@/views/book/BookCancelModal.vue";

const userStore = useUserStore();
const bookList = reactive([]);

// 예약 목록 가져오기
const loadBookList = async (userSeq) => {
  try {
    const data = await fetchBookList(userSeq);
    Object.assign(bookList, data);
  } catch (error) {
    console.error("예약 리스트를 불러오는데 실패했습니다.", error);
  }
}

// 마감 시간 포맷팅
const closedTime = (dateTime) => {
  return dateTime.split("T")[1].substring(0, 5);
}

// 현재 시점이 예약 취소가 가능한지 확인
const isCancelAvailable = (book) => {
  const currentTime = new Date();
  const closedAt = new Date(book.shopClosedAt);
  console.log(currentTime);
  console.log(closedAt);
  const oneHourInMs = 60 * 60 * 1000;
  return closedAt - currentTime >= oneHourInMs;
}

// 예약 취소 모달
const isModalOpen = ref(false);
const selectedBookSeq = ref(null);

const openModal = (book) => {
  if (isCancelAvailable(book) && book.isBookCancelled === 'N') {
    isModalOpen.value = true;
    selectedBookSeq.value = book.bookSeq;
  }
};

const closeModal = () => {
  isModalOpen.value = false;
  selectedBookSeq.value = null;
}

const updateIsBookCancelled =  (bookSeq) => {
  const book = bookList.find(b => b.bookSeq === bookSeq);
  if (book) {
    book.isBookCancelled = 'Y'; // 취소 상태로 업데이트
  }
}

onMounted(async () => {
  const userSeq = userStore.userSeq;

  if (!userSeq) {
    console.error('유효하지 않은 사용자 정보입니다.');
    return;
  }

  try {
    await loadBookList(userSeq);
    console.log("예약 리스트 로드 성공");
  } catch (error) {
    console.error('북마크 로드 중 오류:', error);
  }
})
</script>

<template>
  <div v-if="isModalOpen" class="modal-background" @click="closeModal">
    <BookCancelModal
        :bookSeq="selectedBookSeq"
        @close="closeModal"
        @bookingCancelled="updateIsBookCancelled"
        @click.stop
    />
  </div>
  <MyPageBox>
    <template #action>
      <div>
        <div class="book-list-title">예약 내역</div>
        <hr class="brown-hr">
        <div v-for="book in bookList" :key="book.bookSeq">
          <div class="flex book-list-box">
            <div>
              <img class="shop-img" :src="book.shopImgUrl" alt="매장 이미지">
            </div>
            <div class="flex book-info-box">
              <div class="shop-name">{{ book.shopName }}</div>
              <div class="flex">
                <div class="product-name">{{ book.productName }}</div>
                <div class="book-quantity">{{ book.bookQty }}개</div>
              </div>
              <div class="shop-info-box">
                <div>마감 시간 | -{{ closedTime(book.shopClosedAt) }}</div>
                <div>매장 주소 | {{ book.shopAddress }}</div>
              </div>
            </div>
            <div class="flex book-cancel-button">
              <button
                  v-if="isCancelAvailable(book)"
                  class="cancel-button"
                  :disabled="book.isBookCancelled === 'Y'"
                  @click="openModal(book)"
              >
                {{ book.isBookCancelled === 'Y' ? "취소 완료" : "예약 취소" }}
              </button>
              <div v-else class="unavailable-message flex">
                <div>예약 취소 가능 시간이</div>
                <div>지났습니다.</div>
              </div>

            </div>
          </div>
          <hr class="grey-hr">
        </div>
      </div>
    </template>
  </MyPageBox>
</template>

<style scoped>
.flex {
  display: flex;
}

.modal-background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
  z-index: 100;
  display: flex;
  justify-content: center;
  align-items: center;
}

.book-list-title {
  font-size: 30px;
}

.brown-hr {
  color: var(--button-brown);
  border: 1px solid;
  width: 837px;
  margin: 12px 0;
}

.book-list-box {
  margin: 23px 0 23px 34px;
}

.shop-img {
  width: 121px;
  height: 121px;
  margin-right: 31px;
}

.book-info-box {
  flex-direction: column;
  justify-content: space-between;
  margin: 8px 0;
  width: 492px;
}

.shop-info-box > div {
  margin: 3px 0;
}

.shop-name {
  font-size: 20px;
}

.product-name {
  font-size: 15px;
  margin-right: 10px;
}

.book-quantity {
  color: var(--text-red);
  font-size: 14px;
}

.shop-info-box {
  font-size: 15px;
}

.unavailable-message {
  flex-direction: column;
  align-items: center;
  width: 146px;
}

.unavailable-message > div:first-child {
  margin-bottom: 5px;
}

.cancel-button {
  font-family: "Gowun Dodum";
  width: 103px;
  height: 42px;
  border: none;
  border-radius: 10px;
  background-color: var(--button-brown);
  color: var(--text-white);
  margin-left: 18px;
  cursor: pointer;
}

.book-cancel-button {
  align-items: center;
}

.cancel-button:disabled {
  background-color: var(--button-dark-gray);
  cursor: auto;
}

.grey-hr {
  color: var(--button-gray);
  border: 1px solid;
  width: 837px;
}
</style>