<script setup>
import {ref, onMounted, reactive} from 'vue';
import axios from "axios";
import '@/assets/css/reset.css';
import router from "@/router/index.js";

// ========= 지도 관련 기능 =========
const { VITE_KAKAO_MAP_KEY } = import.meta.env;
const { VITE_KAKAO_MAP_KEY_ALL } = import.meta.env;
const map = ref(null);
const currentLocation = ref([]);
const shops = ref([]);
const markerImageByCategory = {
  1: 'https://goruna.s3.us-west-1.amazonaws.com/a190bcce-1f8b-4216-9248-3e0782fbf9df_icons8-location-48.png',
  2: 'https://goruna.s3.us-west-1.amazonaws.com/1db52cc6-c91a-46e0-829a-413640a7e39f_icons8-location-48%20%283%29.png',
  3: 'https://goruna.s3.us-west-1.amazonaws.com/7ba60c36-0a8a-4dd5-9843-ac35769ddd77_icons8-location-48%20%282%29.png',
  4: 'https://goruna.s3.us-west-1.amazonaws.com/bbbf6a31-9231-4595-a16d-69f8c297eeab_icons8-location-48%20%281%29.png'
}

const loadKakaoMap = (container, userCoords) => {
  const script = document.createElement('script');
  script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${VITE_KAKAO_MAP_KEY}&autoload=false`;
  document.head.appendChild(script);

  script.onload = () => {
    window.kakao.maps.load(() => {
      const options = {
        center: new window.kakao.maps.LatLng(userCoords.latitude, userCoords.longitude),  // 현재 위치 정보를 이용해 지도 중심 설정
        level: 3,  // 지도 확대 수준 설정
        maxLevel: 5,  // 최대 확대 수준 설정
      }

      const mapInstance = new window.kakao.maps.Map(container, options);
      map.value = mapInstance;  // map에 생성된 지도 인스턴스 저장

      // 현재 위치에 마커 추가
      addCurrentLocationMarker(userCoords, mapInstance);

      // 지도에 매장 표시
      displayShopMarkers(mapInstance);
    })
  }
}

// 현재 위치 가져오기
const getCurrentLocation = () => {
  return new Promise((resolve, reject) => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
          position => {
            resolve(position.coords);
          },
          error => {
            reject(error);
          },
          {
            enableHighAccuracy: true,  // GPS 정확도 높이기
            timeout: 5000, // 요청 시간 초과 설정(밀리초) (5초 안에 위치 정보를 받지 않으면 오류 발생)
            maximumAge: 0  // 위치 정보 캐시 사용하지 않도록 설정 (이전에 받은 위치 정보 재사용하지 않도록 설정)
          }
      );
    } else {
      reject(new Error("위치 정보를 가져오지 못했습니다."));
    }
  })
}

// 현재 위치에 마커 표시
const addCurrentLocationMarker = (coords, mapInstance) => {
  const currentLocation = new window.kakao.maps.LatLng(coords.latitude, coords.longitude); // coords로 받은 위도, 경도로 위치 객체 생성

  // 마커 객체 생성 (마커 위치는 currentLocation으로 설정)
  const marker = new window.kakao.maps.Marker({
    position: currentLocation,
  });

  // 지도에 마커 추가
  marker.setMap(mapInstance);

  const markerImage = new window.kakao.maps.MarkerImage(
      'https://goruna.s3.us-west-1.amazonaws.com/46ef7c04-0939-4f2d-b7b7-eed2fd38eb81_user-solid.png',
      new window.kakao.maps.Size(32, 38), // 마커 크기
      { offset: new window.kakao.maps.Point(16, 32) }
  );
  marker.setImage(markerImage);
}

// 주소를 위도/경도로 변환하는 함수
const getCoordinatesFromAddress = async(address) => {
  try {
    const response = await fetch(
        `https://dapi.kakao.com/v2/local/search/address.json?query=${encodeURIComponent(address)}`,
        {
          headers: {
            Authorization: `KakaoAK ${VITE_KAKAO_MAP_KEY_ALL}`,
          },
        }
    );
    const data = await response.json();

    if (data.documents && data.documents.length > 0) {
      const { x: longitude, y: latitude } = data.documents[0].address;

      return { latitude, longitude };
    }
  } catch (error) {
    console.log("주소 변환 도중 오류 발생", error)
  }
}

// 지도 반경에 있는 가게에 마커 표시
const displayShopMarkers = async(mapInstance) => {
  try {
    const response = await axios.get(`http://localhost:8100/api/v1/shop`);
    shops.value = response.data.data;

    for (const shop of shops.value) {
      const coords = await getCoordinatesFromAddress(shop.shopAddress);
      if (coords) {
        const shopLocation = new window.kakao.maps.LatLng(coords.latitude, coords.longitude);

        // 카테고리 별로 마커 이미지 선택
        const markerImage = new window.kakao.maps.MarkerImage(
            markerImageByCategory[shop.categorySeq], new window.kakao.maps.Size(38, 38),
            {offset: new window.kakao.maps.Point(16, 32)}
        )

        // 마커 생성
        const marker = new window.kakao.maps.Marker({
          position: shopLocation,
          image:markerImage,
          map: mapInstance,
        })

        // 마커에 hover할 시 띄어줄 infoWindow
        const overlayContent = `
          <div class="custom_overlay" style="background-color: var(--ivory-background); border: 1px solid var(--button-brown)">
            <div class="custom_overlay_content" style="padding: 10px;">
                <p style="font-weight: bold; margin: 0 0 10px 0">${shop.shopName}</p>
                <p>${shop.shopAddress}</p>
                <div class="flex" style="margin-top: 8px;">
                    <button id="more_button" @click="seeMore()" style="background-color: var(--yellow); border: none; border-radius: 10px; font-family: 'Gowun Dodum'; cursor: pointer">더보기</button>
                    <button id="close_button" style="background-color: var(--button-brown); border: none; border-radius: 10px; color: var(--text-white); font-family: 'Gowun Dodum'; cursor: pointer">닫기</button>
                </div>
            </div>
          </div>
        `;

        // 마커에 hover할 시 띄어줄 infoWindow 생성
        const customOverlay = new window.kakao.maps.CustomOverlay({
          position: shopLocation,
          content: overlayContent,
          map: mapInstance,
          yAnchor: 1.5,  // 마커 상단을 기준으로 CustomOverlay 위치 설정
          clickable: true
        })
        customOverlay.setMap(null);

        // 마커에 hover시 infoWindow 열리도록 이벤트 등록
        window.kakao.maps.event.addListener(marker, 'mouseover', () => {
          customOverlay.setMap(mapInstance);
        });


        document.addEventListener('click', (event) => {
          // 닫기 버튼을 누르면 infoWindow 닫히도록 이벤트 등록
          if (event.target.id === 'close_button') {
            customOverlay.setMap(null);
          }

          // 더보기 버튼을 누르면 해당 매장 상세 페이지로 라우팅되도록 이벤트 등록
          if(event.target.id === 'more_button') {
              router.push(`/category/${shop.categorySeq}/shop/${shop.shopSeq}`);

          }
        });
      }
    }
  } catch(error) {
    console.log("매장 조회 실패", error);
  }
}

// ========= 오늘의 특가 리스트 가져오기 =========
const todaySaleProducts = reactive([]);
const fetchTodaySaleList = async() => {
  try {
    const response = await axios.get(`http://localhost:8100/api/v1/shop`);
    const todaySaleList = response.data.data;

    todaySaleList.forEach(product => {
      todaySaleProducts.push({
        shopName: product.shopName,
        shopImgUrl: product.shopImgUrl,
        shopAddress: product.shopAddress,
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

onMounted(async() => {
  try {
    fetchTodaySaleList();

    const coords = await getCurrentLocation();
    currentLocation.value = coords;
    loadKakaoMap(map.value, coords); // 지도를 로드하고 위치 전달하여 마커 표시
  } catch (error) {
    console.log("현재 위치를 가져오는 데 실패했습니다.", error);
  }
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