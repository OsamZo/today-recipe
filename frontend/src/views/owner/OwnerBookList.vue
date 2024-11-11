<script setup>
import '@/assets/css/reset.css';
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome';
import {computed} from "vue";


const props = defineProps({
  books: {
    type: Array,
    required: true
  }
});

const groupedBooksByDate = computed(() => {
  const groups = {};
  props.books.forEach(book => {
    const date = book.regDate; // regDate를 기준으로 그룹화
    if (!groups[date]) {
      groups[date] = [];
    }
    groups[date].push(book); // 같은 날짜의 책을 배열로 묶기
  });
  return groups;
});

const getBookDate = (dateTime) => {
  return dateTime.split("T")[0];
}



</script>

<template>
  <div class="total-content">
    <div class="page-content-title">
      <div class="menu-title">예약 정보 확인</div>
    </div>
    <!-- 날짜별 리스트  시작 -->
    <div class="padding">
      <div v-for="(groupedBooks, date) in groupedBooksByDate" :key="date">
        <div class="inline-date">
          <div><font-awesome-icon :icon="['far', 'calendar']" /></div>
          <div>{{ getBookDate(date) }}</div> <!-- 날짜 출력 -->
        </div>

        <div class="inline-list" v-for="book in groupedBooks" :key="book.bookSeq">
          <div>{{ book.userNickname }}</div>
          <div>{{ book.productName }} X {{ book.bookQty }}</div>
          <div>{{ book.totalPrice }}</div>
          <button
              v-if="book.bookIsProductReceived === 'Y'"
              class="check-button">
            수령
          </button>
          <button
              v-if="book.bookIsProductReceived === 'N'"
              class="check-button"
              @click="">
            미수령
          </button>
        </div>
      </div>
    </div>
  </div>
  <!-- 날짜별 리스트  끝 -->

</template>


<style scoped>
.padding {
  padding: 0px 50px;
}

.inline-list div {
  font-size: 20px;
}

.check-button-red {
  border: 1px var(--text-red) solid;
  height: 33px;
  border-radius: 10px;
  text-align: center;
  justify-content: center;
  display: flex;
  align-items: center;
  width: 80px;
}

.check-button {
  background-color: var(--text-white);
  border: 1px var(--button-brown) solid;
  border-radius: 10px;
  height: 33px;
  text-align: center;
  justify-content: center;
  display: flex;
  align-items: center;
  width: 80px;
}

.inline-list {
  display: flex;
  align-items: center;
  justify-content: space-around;
  padding: 0px 50px;
  margin: 8px 0px;
}

.inline-date {
  display: flex;
  align-items: center;
  font-size: 30px;
  color: var(--button-brown);
  margin-bottom: 20px;
}

.inline-date div {
  margin: 0px 5px;
}

.total-content {
  width: 100%;
}

.page-content-title {
  display: flex;
  padding-bottom: 12px;
  border-bottom: 1px solid var(--button-brown);
  align-items: center;
  margin-bottom: 130px
}

.menu-title {
  font-size: 30px;
}
</style>