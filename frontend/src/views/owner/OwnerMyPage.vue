<script setup>
import '@/assets/css/reset.css';
import {computed, reactive, ref} from "vue";
import OwnerBookList from "@/views/owner/OwnerBookList.vue";
import ShopInfo from "@/views/owner/ShopInfo.vue";
import OwnerProduct from "@/views/owner/OwnerProduct.vue";
import {useShopStore} from "@/store/ShopStore.js";
import {useUserStore} from "@/store/UserStore.js";
import {useBookStore} from "@/store/BookStore.js";
import {useRouter} from 'vue-router';


// 메뉴 선택 시 화면 전환
const router = useRouter();
const changedView = (viewUrl) => {
  router.push(viewUrl);
}

// 메뉴 선택 시 component 전환
const selectedMenu = ref('userInfo');

const userStore = useUserStore();
const bookStore = useBookStore();

const selectMenu = async (menu) => {
  console.log(menu);
  selectedMenu.value = menu;
  if (selectedMenu.value === 'ShopInfo') {
    await shopStore.loadOnwerShopData(userStore.userSeq);
  } else if (selectedMenu.value === 'OwnerBookList') {
    await loadBookList(userStore.userSeq);
    // receiveOwnerShopBooks(userStore.userSeq);
  }
};

// (사장) 매장 정보 조회, 수정, 삭제
const shopStore = useShopStore();
const shopData = computed(() => shopStore.shopData);

const receivedShopData = ref({
  shopTel: '',
  shopAddress: '',
  shopIntroduction: ''
});
const updateShopData = async (inputShopData) => {
  const shopSeq = shopData.value.shopSeq;
  receivedShopData.value = inputShopData;
  // console.log(shopSeq, receivedShopData.value);
  await shopStore.updateOwnerShopData(shopSeq, receivedShopData.value);
  await selectMenu('ShopInfo');
};
const propsComponent = ref('readShop');

const deleteShop = async () => {
  const shopSeq = shopData.value.shopSeq;
  console.log(shopSeq);
  await shopStore.deleteOwnerShopData(shopSeq);
  selectedMenu.value = 'userInfo';
};

// (사장) 내 매장 예약 목록
const bookList = reactive([]);

const loadBookList = async (userSeq) => {
  const books = await bookStore.loadReceivedBooks(userSeq);

  bookList.length = 0;
  // console.log(books) 로그 정상
  books.forEach(book => {
    bookList.push({
      bookSeq: book.bookSeq,
      userNickname: book.userNickname,
      productName: book.productName,
      bookQty: book.bookQty,
      totalPrice: book.totalPrice,
      regDate: book.regDate,
      bookIsProductReceived: book.bookIsProductReceived
    });
  });
};

const receivedProduct = async(updateData) => {
  // console.log(updateData); // 정상 로그
  const bookSeq = updateData.bookSeq;
  const bookIsProductReceived = { bookIsProductReceived: updateData.bookIsProductReceived };
  await bookStore.updateReceivedStatus(bookSeq, bookIsProductReceived);
  await selectMenu('OwnerBookList');
}

</script>

<template>
  <div class="page">
    <div class="page-title">마이페이지</div>
    <div class="white-container">

      <div class="menu-container">
        <div class="left-menu">
          <div class="user-info">내 정보</div>
          <div
              class="menu-button"
              @click="changedView('/owner')">
            회원 정보 조회
          </div>
          <div
              class="menu-button"
              @click="changedView('/owner/book')">
            예약 내역
          </div>
          <div
              class="menu-button"
              @click="changedView('/owner/history')">
            나의 이용 내역
          </div>
          <div
              class="menu-button"
              @click="changedView('/owner/review')">
            나의 리뷰 조회
          </div>

          <br>

          <div class="user-info">내 정보</div>
          <div
              class="menu-button"
              :class="{ 'active': selectedMenu === 'OwnerBookList' }"
              @click="selectMenu('OwnerBookList')">
            예약 정보 확인
          </div>
          <div
              class="menu-button"
              :class="{ 'active': selectedMenu === 'ShopInfo' }"
              @click="selectMenu('ShopInfo')">
            매장 정보
          </div>
          <div
              class="menu-button"
              :class="{ 'active': selectedMenu === 'OwnerProduct' }"
              @click="selectMenu('OwnerProduct')">
            매장 관리
          </div>
        </div>

        <div class="menu-content">
          <RouterView/>
          <OwnerBookList
              v-if="selectedMenu === 'OwnerBookList'"
              :books="bookList"
              @received-product="receivedProduct"
              />
          <ShopInfo
              v-else-if="selectedMenu === 'ShopInfo'"
              :shopData="shopData"
              :propsComponent="propsComponent"
              @update-shopInfo="updateShopData"
              @update-product="selectMenu('OwnerProduct')"
              @delete-shop="deleteShop"/>
          <OwnerProduct v-else-if="selectedMenu === 'OwnerProduct'"/>
          <!-- 다른 컴포넌트들 추가해서 사용하세요 -->
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.left-menu {
  margin-right: 70px;
}

.user-info {
  font-size: 30px;
  color: var(--text-black);
  margin-bottom: 15px;
}

.page {
  flex-direction: column;
  align-items: center;
  display: flex;
  width: 100%;
}

.page-title {
  font-size: 40px;
  color: var(--text-black);
  margin: 80px 0px 40px 0px;
}

.white-container {
  border-radius: 43px;
  background: #FFF;
  box-shadow: 0px 4px 13px 0px rgba(0, 0, 0, 0.13) inset;
  padding: 70px;
  width: 80%;
}

.menu-content {
  width: 100%;
}

.menu-container {
  display: flex;
}

.menu-button {
  margin-left: 45px;
  font-size: 25px;
  white-space: nowrap;
  margin-top: 10px;
}
</style>