<script setup>
import '@/assets/css/reset.css';
import {RouterLink, useRouter} from 'vue-router';
import {onMounted, reactive, ref} from "vue";
import {fetchCategoryList, fetchShopListByCategory} from "@/api/shop/ShopListReadApi.js";

const router = useRouter();
const selectedCategory = ref(1);
const shopList = reactive([]);
const categoryList = reactive([]);
const orderBy = ref('latest');
const searchKeyword = ref("");

// 매장 리스트 가져오기
const loadShopList = async (categorySeq, orderBy, searchKeyword) => {
  const shops = await fetchShopListByCategory(categorySeq, orderBy, searchKeyword);

  shopList.length = 0;

  shops.forEach(shop => {
    shopList.push({
      shopSeq: shop.shopSeq,
      shopName: shop.shopName,
      shopImgUrl: shop.shopImgUrl,
      shopAddress: shop.shopAddress,
      categorySeq: shop.categorySeq,
      categoryName: shop.categoryName,
      productOriginalPrice: shop.productOriginalPrice,
      productSalePrice: shop.productSalePrice
    });
  });
};

const changeOrderBy = () => {
  loadShopList(selectedCategory.value, orderBy.value);
}

// 카테고리 리스트 가져오기
const loadCategoryList = async () => {
  const categories = await fetchCategoryList();
  categoryList.length = 0;
  categories.forEach(category => {
    categoryList.push({
      categorySeq: category.categorySeq,
      categoryName: category.categoryName
    });
  });
};

// 카테고리 버튼 클릭 시 변경
const changeCategory = (categorySeq) => {
  selectedCategory.value = categorySeq;
  routeByCategory(selectedCategory.value);
  loadShopList(categorySeq);
}

// 가격 포맷팅 함수
const formatPrice = (price) => {
  if (price === undefined || price === null) {
    return '가격 정보가 없습니다';
  }
  return price.toLocaleString();
}

const routeByCategory = (categorySeq) => {
  router.push(`/category/${categorySeq}/shop`);
}

// 검색어 변경 시 처리
const search = () => {
  loadShopList(selectedCategory.value, orderBy.value, searchKeyword.value);
};

onMounted(() => {
  loadCategoryList();
  loadShopList(selectedCategory.value, orderBy.value);
})
</script>

<template>
  <div class="content">
    <aside class="flex">
      <div class="search-bar flex">
        <input
            class="search-input"
            v-model="searchKeyword"
            type="text"
            placeholder="찾고있는 정보가 있나요?"
            required
            @keydown.enter="search">
        <div @click="search" type="submit">
          <img class="search-img" src="https://goruna.s3.us-west-1.amazonaws.com/1c054290-0dda-44f7-b7b4-f9e2f009ec79_search.png" alt="검색 돋보기">
        </div>
      </div>
      <div class="sort-box">
        <select v-model="orderBy" @change="changeOrderBy" class="sort-box-select">
          <option value="latest">최신순</option>
          <option value="popular">인기순</option>
        </select>
      </div>
    </aside>
    <hr class="brown-hr">
    <article>
      <div class="category_box_list flex">
        <button
            @click="changeCategory(category.categorySeq)"
            v-for="category in categoryList"
            :key="category.categorySeq"
            class="category_box"
            :class="{ selected: selectedCategory === category.categorySeq }"
        >
          {{ category.categoryName }}
        </button>
      </div>
      <div class="shop_list_box">
        <div v-for="shop in shopList" :key="shop.shopSeq" class="shop_white_background">
          <RouterLink
              :to="{path: `shop/${shop.shopSeq}`, query: {productSeq: shop.productSeq}}">
            <div class="flex shop_title_box">
              <div class="shop_name">{{ shop.shopName }}</div>
              <div class="category_name">{{ shop.categoryName }}</div>
            </div>
            <div>
              <img :src="shop.shopImgUrl" alt="매장 이미지" class="shop_img">
            </div>
            <div class="price_box">
              <div class="sale_price_title">할인가</div>
              <div class="flex price_text_box">
                <div class="original_price_box">
                  <div class="original_price">{{ formatPrice(shop.productOriginalPrice) }}원</div>
                  <svg width="74" height="16" viewBox="0 0 74 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M73.7071 8.70711C74.0976 8.31658 74.0976 7.68342 73.7071 7.29289L67.3431 0.928932C66.9526 0.538408 66.3195 0.538408 65.9289 0.928932C65.5384 1.31946 65.5384 1.95262 65.9289 2.34315L71.5858 8L65.9289 13.6569C65.5384 14.0474 65.5384 14.6805 65.9289 15.0711C66.3195 15.4616 66.9526 15.4616 67.3431 15.0711L73.7071 8.70711ZM0 9H73V7H0V9Z" fill="#EB4335"/>
                  </svg>
                </div>
                <div class="sale_price">{{ formatPrice(shop.productSalePrice) }}원</div>
              </div>
            </div>
          </RouterLink>
        </div>
      </div>
    </article>
  </div>
</template>

<style scoped>
.flex {
  display: flex;
}

.search-bar {
  width: 764px;
  height: 44px;
  border-radius: 20px;
  background-color: var(--box-grey);
  padding: 0 20px;
  align-items: center;
  margin: 0 30px 49px 0;
}

.search-input {
  font-family: "Gowun Dodum";
  width: 740px;
  border: none;
  background-color: transparent;
  text-align: center;
}

.search-img {
  width: 30px;
  height: 30px;
  border: none;
  background-color: transparent;
}

.sort-box-select {
  padding: 0 30px;
  font-family: "Gowun Dodum";
  width: 125px;
  height: 44px;
  background-color: var(--yellow);
  font-size: 16px;
  border: none;
  border-radius: 52px;
}

.content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  max_width: 1903px;
  width: 100%;
  margin: 88px 0 0 0;
}

.brown-hr {
  border: 1px solid var(--button-brown);
  width: 948px;
}

.category_box {
  width: 84px;
  height: 31px;
  background-color: var(--yellow);
  font-size: 15px;
  border-radius: 15px;
  justify-content: center;
  margin: 25px 10px 25px 20px;
  border: none;
  font-family: "Gowun Dodum";
  cursor: pointer;
}

.category_box.selected {
  background-color: var(--button-brown);
  color: var(--white-background);
}

.shop_list_box {
  display: grid;
  grid-template-columns: 280px 280px 280px;
  grid-template-rows: 380px 380px;
  grid-column-gap: 40px;
  grid-row-gap: 40px;
}

.shop_white_background {
  width: 267px;
  height:373px;
  background-color: var(--white-background);
  border-radius: 16px;
  padding: 26px 23px 15px 23px;
}

.shop_title_box {
  justify-content: space-between;
  margin-bottom: 16px;
}

.shop_name {
  font-size: 30px;
}

.category_name{
  font-size: 15px;
  height: 15px;
  margin-top: 16px;
}

.shop_img {
  width: 220px;
  height: 220px;
}

.price_box {
  font-size: 20px;
  width: 158px;
  margin: 15px auto;
}

.price_text_box {
  justify-content: space-between;
  align-items: center;
}

.sale_price_title {
  color: var(--text-red);
  font-size: 20px;
  text-align: center;
  margin-bottom: 10px;
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
  margin-left: 10px;
}

.sale_price {
  font-size: 20px;
  display: flex;
}
</style>