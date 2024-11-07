<script setup>
import {ref, onMounted, reactive} from 'vue';
import axios from "axios";
import '@/assets/css/reset.css';

// 지도 관련 기능
const { VITE_KAKAO_MAP_KEY } = import.meta.env;
const map = ref(null);

const loadKakaoMap = (container) => {
  const script = document.createElement('script');
  script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${VITE_KAKAO_MAP_KEY}&autoload=false`;
  document.head.appendChild(script);

  script.onload = () => {
    window.kakao.maps.load(() => {
      const options = {
        center: new window.kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
        maxLevel: 5,
      }

      const mapInstance = new window.kakao.maps.Map(container, options);
    })
  }
}

// 오늘의 특가 리스트 가져오기
const todaySaleProducts = reactive([]);
const fetchTodaySaleList = async() => {
  try {
    const response = await axios.get(`http://localhost:8100/api/v1/shop`);
    const todaySaleList = response.data.data;

    todaySaleList.forEach(product => {
      todaySaleProducts.push({
        shopName: product.shopName,
        shopImgUrl: product.shopImgUrl,
        categoryName:product.categoryName,
        productOriginalPrice: product.productOriginalPrice,
        productSalePrice: product.productSalePrice
      })
    })
  } catch(error) {
    console.log("오늘의 특가 리스트를 불러오던 중 오류 발생", error);
  }
}

// 가격 포맷팅 함수
const formatPrice = (price) => {
  if (price === undefined || price === null) {
    return '가격 정보가 없습니다';
  }
  return price.toLocaleString();
}

onMounted(() => {
  fetchTodaySaleList();
  loadKakaoMap(map.value);
})
</script>

<template>
  <aside>
    <div>
      <img
          class="main_banner"
          src="https://goruna.s3.us-west-1.amazonaws.com/935b1a55-b212-4202-bf4e-2953749751be_mainBanner.png"
          alt="메인페이지 배너">
    </div>
  </aside>
  <article class="main_article">
    <div class="main_article_content">
      <div class="sale_list_box">
        <div class="flex sale_products_list_text_box">
          <div class="section_title">오늘의 특가 리스트</div>
          <div>둘러보기 ></div>
        </div>
        <ul class="flex sale_list">
          <li v-for="product in todaySaleProducts">
            <div class="product_title_box">
              <div class="product_name">{{ product.shopName}}</div>
              <div class="category_name">{{ product.categoryName }}</div>
            </div>
            <div>
              <img class="sale_shop_img" :src="product.shopImgUrl" alt="매장 이미지">
            </div>
            <div class="price_box">
              <div class="sale_price_title">할인가</div>
              <div class="flex price_text_box">
                <div class="original_price_box">
                  <div class="original_price">{{ formatPrice(product.productOriginalPrice) }}원</div>
                  <svg width="74" height="16" viewBox="0 0 74 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M73.7071 8.70711C74.0976 8.31658 74.0976 7.68342 73.7071 7.29289L67.3431 0.928932C66.9526 0.538408 66.3195 0.538408 65.9289 0.928932C65.5384 1.31946 65.5384 1.95262 65.9289 2.34315L71.5858 8L65.9289 13.6569C65.5384 14.0474 65.5384 14.6805 65.9289 15.0711C66.3195 15.4616 66.9526 15.4616 67.3431 15.0711L73.7071 8.70711ZM0 9H73V7H0V9Z" fill="#EB4335"/>
                  </svg>
                </div>
                <div class="sale_price">{{ formatPrice(product.productSalePrice) }}원</div>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <div>
        <div class="section_title">가까운 매장 찾기</div>
        <div class="map" ref="map"></div>
      </div>
    </div>
  </article>
</template>

<style scoped>
.flex {
  display: flex;
}

.main_banner {
  width: 100%;
}

.main_article {
  width: 90%;
  max-width: 1193px;
  margin: 126px auto;
}

.sale_list_box {
  margin-bottom: 76px;
}

.sale_products_list_text_box {
  justify-content: space-between;
  align-items: center;
}

.section_title {
  font-size: 30px;
}

.sale_list {
  justify-content: space-evenly;
  margin-top: 15px;
}

.product_title_box {
  margin: 0 0 8px 15px;
}

.product_name {
  font-size: 25px;
}

.category_name {
  font-size: 17px;
  margin-top: 7px;
}

.sale_shop_img {
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
}

.map {
  width: 100%;
  height: 560px;
  margin-top: 36px;
}
</style>