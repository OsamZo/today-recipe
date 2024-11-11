<script setup>
import MyPageBox from "@/components/MyPageBox.vue";
import {fetchUserHistory, fetchUserInfo} from "@/api/user/UserApi.js";
import {onMounted, reactive} from "vue";
import {useUserStore} from "@/store/UserStore.js";

const userStore = useUserStore();
const userInfo = reactive([]);
const userHistoryList = reactive([]);

const loadUserInfo = async (userSeq) => {
  try {
    const data = await fetchUserInfo(userSeq);
    Object.assign(userInfo, data);
  } catch (error) {
    console.log("회원 정보를 불러오는데 실패했습니다.", error);
  }
}

const loadHistory = async (userSeq) => {
  try {
    const data = await fetchUserHistory(userSeq);
    Object.assign(userHistoryList, data);
  } catch (error) {
    console.log("회원 이용 내역을 불러오는데 실패했습니다.", error);
  }
}

// 가격 포맷팅 함수
const formatPrice = (price) => {
  if (price === undefined || price === null) {
    return '가격 정보가 없습니다';
  }
  return price.toLocaleString();
}

// 예약 날짜만 가져오기
const getBookDate = (dateTime) => {
  return dateTime.split("T")[0];
}

onMounted(async () => {
  const userSeq = userStore.userSeq;

  if (!userSeq) {
    console.error('유효하지 않은 사용자 정보입니다.');
    return;
  }

  await loadUserInfo(userSeq);
  await loadHistory(userSeq);
})
</script>

<template>
  <MyPageBox>
    <template #action>
      <div>
        <div class="content-title">나의 이용 내역</div>
        <hr class="brown-hr">
        <div class="content flex">
          <div class="flex boxes">
            <div class="box saved-money-box flex">
              <div class="box-title">절약한 금액</div>
              <div class="box-content">{{ formatPrice(userInfo.userSavedMoney) }}원</div>
            </div>
            <div class="box user-usage-count-box flex">
              <div class="box-title">이용한 횟수</div>
              <div class="box-content">{{ userInfo.userUsageCount }}번</div>
            </div>
          </div>
          <div>
            <div v-for="userHistory in userHistoryList" :key="userHistory.bookmarkSeq">
              <div class="flex history-box">
                <div>
                  <img class="shop-img" :src="userHistory.shopImgUrl" alt="매장 이미지">
                </div>
                <div class="history-content">
                  <div>{{ getBookDate(userHistory.regDate) }}</div>
                  <div class="flex shop-name-box">
                    <div class="shop-name">{{ userHistory.shopName }}</div>
                    <button class="write-review-button">리뷰 쓰기</button>
                  </div>
                  <div class="flex">
                    <div>{{ userHistory.productName }}</div>
                    <div class="book-qty">{{ userHistory.bookQty }}개</div>
                  </div>
                  <div class="price-box">
                    <div class="flex price-text-box">
                      <div class="original-price-box">
                        <div class="original-price">{{ formatPrice(userHistory.productOriginalPrice) }}원</div>
                        <svg width="74" height="16" viewBox="0 0 74 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                          <path
                              d="M73.7071 8.70711C74.0976 8.31658 74.0976 7.68342 73.7071 7.29289L67.3431 0.928932C66.9526 0.538408 66.3195 0.538408 65.9289 0.928932C65.5384 1.31946 65.5384 1.95262 65.9289 2.34315L71.5858 8L65.9289 13.6569C65.5384 14.0474 65.5384 14.6805 65.9289 15.0711C66.3195 15.4616 66.9526 15.4616 67.3431 15.0711L73.7071 8.70711ZM0 9H73V7H0V9Z"
                              fill="#EB4335"/>
                        </svg>
                      </div>
                      <div class="sale-price">{{ formatPrice(userHistory.productSalePrice) }}원</div>
                    </div>
                    <div class="saled-price">({{
                        formatPrice(userHistory.productOriginalPrice - userHistory.productSalePrice)
                      }}원 할인)
                    </div>
                  </div>
                </div>
                <div class="flex total-price-box">
                  <div class="total-price">총액</div>
                  <div class="original-total-price">
                    {{ formatPrice(userHistory.productOriginalPrice * userHistory.bookQty) }}원
                  </div>
                  <div class="saled-total-price">
                    {{ formatPrice(userHistory.productSalePrice * userHistory.bookQty) }}원
                  </div>
                </div>
              </div>
              <hr class="grey-hr">
            </div>
          </div>
        </div>
      </div>
    </template>
  </MyPageBox>
</template>

<style scoped>
.flex {
  display: flex;
}

.content-title {
  font-size: 30px;
  margin: 0 0 14px 20px;
}

.brown-hr {
  border: 1px solid var(--button-brown);
  width: 837px;
}

.content {
  justify-content: center;
  flex-direction: column;
  margin: 0 33px;
}

.box {
  width: 306px;
  height: 177px;
  border-radius: 10px;
  margin: 33px 24px;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.boxes {
  justify-content: center;
}

.history-content {
  width: 400px;
}

.box-title {
  font-size: 30px;
  margin-bottom: 12px;
}

.box-content {
  font-size: 40px;
  color: var(--light-green);
}

.saved-money-box {
  background-color: var(--light-yellow);
}

.user-usage-count-box {
  background-color: var(--ivory-background);
}

.history-box {
  justify-content: space-between;
  margin: 22px;
}

.shop-img {
  width: 130px;
  height: 130px;
}

.shop-name {
  font-size: 20px;
}

.shop-name-box {
  align-items: center;
  margin: 4px 0;
  justify-content: space-between;
}

.write-review-button {
  font-family: "Gowun Dodum";
  width: 83px;
  height: 29px;
  border-radius: 10px;
  border: 1px solid var(--button-brown);
  background-color: var(--white-background);
  margin: 0 10px;
}

.book-qty {
  margin-left: 20px;
  color: var(--text-red);
}

.price-box {
  font-size: 20px;
  width: 158px;
  margin: 15px 0;
}

.price-text-box {
  justify-content: space-between;
  align-items: center;
}

.original-price-box {
  position: relative;
  top: -8px;
  white-space: nowrap;
}

.original-price-box * {
  position: absolute;
}

.original-price {
  font-size: 15px;
  width: 48px;
  margin-left: 5px;
}

.sale-price {
  font-size: 20px;
}

.saled-price {
  color: var(--text-red);
  font-size: 14px;
  font-weight: bold;
  float: right;
  margin: 2px 0 0 0;
}

.total-price-box {
  flex-direction: column;
  justify-content: space-between;
  margin: 20px 0;
  width: 130px;
}

.total-price {
  font-size: 20px;
}

.original-total-price {
  text-decoration: line-through;
  color: var(--text-gray);
}

.saled-total-price {
  font-weight: bold;
  font-size: 25px;
}

.grey-hr {
  border: 1px solid var(--button-gray);
}
</style>