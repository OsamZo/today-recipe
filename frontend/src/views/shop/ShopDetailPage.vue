<script setup>
import ShopCard from "@/components/ShopCard.vue";
import WhiteContentBox from '@/components/WhiteContentBox.vue';
import BookQuantityModal from "@/views/book/BookQuantityModal.vue";
import {onMounted, reactive, ref, watch} from "vue";
import {useRoute, useRouter} from "vue-router";
import {useBookmarkStore} from "@/store/BookmarkStore.js";
import {useUserStore} from "@/store/UserStore.js";
import {fetchShopDetail} from "@/api/shop/ShopReadApi.js";

const shopDetail = reactive([]);
const route = useRoute();
const router = useRouter();
const userStore = useUserStore();

// 매장 상세정보 데이터를 불러오기
const loadShopDetail = async (categorySeq, shopSeq) => {
  try {
    const data = await fetchShopDetail(categorySeq, shopSeq);
    // 데이터를 shopDetail에 병합
    Object.assign(shopDetail, data);
  } catch (error) {
    console.error("매장 상세 정보를 불러오는데 실패했습니다:", error);
  }
};

// ========== 북마크 ==========
const bookmarkStore = useBookmarkStore();
const isBookmarked = ref(false);
const defaultBookmarkIcon = 'https://goruna.s3.us-west-1.amazonaws.com/b23bd521-8f40-4e42-8d24-17d2372116e2_icons8-bookmark-48%20%281%29.png';
const checkedBookmarkIcon = 'https://goruna.s3.us-west-1.amazonaws.com/22e88df2-b952-4462-a282-39de0b060447_icons8-bookmark-48%20%282%29.png';

// 북마크 등록되어 있는지 체크
const checkIfBookmarked = () => {
  const bookmarkedShop = bookmarkStore.bookmarks.find(bookmark => bookmark.shopSeq === shopDetail.shopSeq);

  // 'isBookmarked.value = bookmarkedShop ? true : false;' 을 아래와 같이 작성 가능
  isBookmarked.value = !!bookmarkedShop;
}

// 북마크 등록/취소
const toggleBookmark = async () => {
  try {
    const bookmarkedShop = bookmarkStore.bookmarks.find(bookmark => bookmark.shopSeq === shopDetail.shopSeq);
    const userSeq = userStore.userSeq;
    if (isBookmarked.value) {
      await bookmarkStore.removeBookmark(userSeq, bookmarkedShop.bookmarkSeq);
      isBookmarked.value = false;
      alert("북마크가 취소되었습니다.");
    } else {
      await bookmarkStore.addBookmark(userSeq, shopDetail.shopSeq, {
        shopName: shopDetail.shopName,
        shopAddress: shopDetail.shopAddress,
        shopImgUrl: shopDetail.shopImgUrl,
        shopTel: shopDetail.shopTel,
        categoryName: shopDetail.categoryName
      });

      isBookmarked.value = true;
      alert("북마크가 등록되었습니다.");
    }
  } catch (error) {
    console.log("북마크 생성 중 오류 발생", error);
  }
}

// 가격 포맷팅 함수
const formatPrice = (price) => {
  if (price === undefined || price === null) {
    return '가격 정보가 없습니다';
  }
  return price.toLocaleString();
}

// 리뷰 목록으로 라우팅
const routeToReviewList = () => {
  router.push(`/review`);
}

// 예약하기 누를 시 모달
const isModalOpen = ref(false);

const closeModal = () => {
  isModalOpen.value = false;
}

const updateQuantity = (updatedQty) => {
  shopDetail.productQty = updatedQty;
}

onMounted(async () => {
  const {categorySeq, shopSeq} = route.params;
  await loadShopDetail(categorySeq, shopSeq);

  const userSeq = userStore.userSeq;

  if (!userSeq) {
    console.error('유효하지 않은 사용자 정보입니다.');
    return;
  }

  try {
    await bookmarkStore.loadBookmarks(userSeq);
    console.log("북마크 로드 성공");
    checkIfBookmarked();
  } catch (error) {
    console.error('북마크 로드 중 오류:', error);
  }
})
</script>

<template>
  <div v-if="isModalOpen" class="modal-background" @click="closeModal">
    <BookQuantityModal
        :productSeq="shopDetail.productSeq"
        :productQty="shopDetail.productQty"
        @close="closeModal"
        @updated-quantity = "updateQuantity"
        @click.stop
    />
  </div>
  <WhiteContentBox>
    <ShopCard
        :shopImgUrl="shopDetail.shopImgUrl"
        :shopName="shopDetail.shopName">
      <template #bookmark>
        <img
            @click="toggleBookmark"
            class="bookmark"
            :src="isBookmarked ? checkedBookmarkIcon : defaultBookmarkIcon"
            alt="북마크 이미지">
      </template>
    </ShopCard>

    <div class="shop_detail_box">
      <div class="review_box" @click="routeToReviewList()">리뷰 {{ shopDetail.shopReviewCount }}개 ></div>
      <div class="shop_info_box">
        <div>마감 시간 | {{ shopDetail.productClosedAt }}</div>
        <div>매장 주소 | {{ shopDetail.shopAddress }}</div>
        <div>매장 소개 | {{ shopDetail.shopIntroduction }}</div>
      </div>
      <hr class="brown_hr">
      <div>
        <div class="product_of_today_title">오늘의 랜덤 박스</div>
        <div class="product_info_box flex">
          <div>
            <img :src="shopDetail.productImgUrl" alt="상품 이미지" class="product_img">
          </div>
          <div class="product_info_text_box">
            <div class="product_name_text">{{ shopDetail.productName }}</div>
            <div class="price_box">
              <div class="flex price_text_box">
                <div class="original_price_box">
                  <div class="original_price">{{ formatPrice(shopDetail.productOriginalPrice) }}원</div>
                  <svg width="74" height="16" viewBox="0 0 74 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path
                        d="M73.7071 8.70711C74.0976 8.31658 74.0976 7.68342 73.7071 7.29289L67.3431 0.928932C66.9526 0.538408 66.3195 0.538408 65.9289 0.928932C65.5384 1.31946 65.5384 1.95262 65.9289 2.34315L71.5858 8L65.9289 13.6569C65.5384 14.0474 65.5384 14.6805 65.9289 15.0711C66.3195 15.4616 66.9526 15.4616 67.3431 15.0711L73.7071 8.70711ZM0 9H73V7H0V9Z"
                        fill="#EB4335"/>
                  </svg>
                </div>
                <div class="sale_price">{{ formatPrice(shopDetail.productSalePrice) }}원</div>
              </div>
              <div class="saled_price">({{
                  formatPrice(shopDetail.productOriginalPrice - shopDetail.productSalePrice)
                }}원 할인)
              </div>
            </div>
            <div class="product_qty">남은 수량: {{ shopDetail.productQty }}개</div>
          </div>
          <div>
            <button
                class="book_button"
                @click="isModalOpen = true"
                :disabled="shopDetail.productQty === 0">
              예약하기
            </button>
          </div>
        </div>
      </div>
      <hr class="grey_hr">
    </div>
  </WhiteContentBox>

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

.bookmark {
  cursor: pointer;
}

.shop_detail_box {
  margin-top: 140px;
  width: 100%;
}

.review_box {
  font-weight: bold;
  font-size: 25px;
  cursor: pointer;
}

.shop_info_box {
  margin-top: 16px;
  margin-bottom: 24px;
}

.shop_info_box > div {
  margin-top: 8px;
  font-size: 18px;
}

.brown_hr {
  border: 1px solid var(--button-brown);
  width: 100%;
}

.product_of_today_title {
  font-size: 30px;
  margin-top: 24px;
  margin-bottom: 18px;
}

.product_info_box {
  padding-left: 20px;
  margin-bottom: 21px;
}

.product_info_text_box {
  padding: 10px 0 9px 21px;
  width: 90%;
}

.product_img {
  width: 121px;
  height: 121px;
}

.product_name_text {
  font-size: 20px;
}

.price_box {
  font-size: 20px;
  width: 158px;
  margin: 15px 0;
}

.price_text_box {
  justify-content: space-between;
  align-items: center;
}

.original_price_box {
  position: relative;
  top: -8px;
  white-space: nowrap;
}

.original_price_box * {
  position: absolute;
}

.original_price {
  font-size: 15px;
  width: 48px;
  margin-left: 5px;
}

.sale_price {
  font-size: 20px;
}

.saled_price {
  color: var(--text-red);
  font-size: 14px;
  font-weight: bold;
  float: right;
  margin: 2px 0 0 0;
}

.product_qty {
  font-size: 15px;
  white-space: nowrap;
  padding: 10px 0 0 0;
}

.book_button {
  width: 76px;
  height: 32px;
  border-radius: 10px;
  background-color: var(--button-brown);
  color: var(--text-white);
  border: none;
  font-family: "Gowun Dodum";
  margin: 42px 10px 0 0;
  cursor: pointer;
}

.book_button:disabled {
  background-color: var(--button-gray);
}

.grey_hr {
  width: 100%;
  border: 0.5px solid var(--button-gray);
  margin-bottom: 100px;
}
</style>