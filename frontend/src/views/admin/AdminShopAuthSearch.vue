  <script setup>
  import { ref, onMounted } from "vue";
  import axios from "axios";
  import AdminNav from "@/components/AdminNav.vue";
  import AdminBodyTitle from "@/components/AdminBodyTitle.vue";

  const shopList = ref([]); // storeList를 ref로 정의하여 응답 데이터를 저장
  const page = 1; // 현재 페이지
  const size = 10; // 페이지당 항목 수

  // 데이터 가져오는 함수 정의
  const fetchShops = async () => {
    try {
      const response = await axios.get('http://localhost:8100/api/v1/admin/shop/auth', {
        params: { page, size }
      });
      shopList.value = response.data.data; // 응답 데이터로 storeList 갱신
    } catch (error) {
      console.error("데이터를 불러오는 중 오류 발생:", error);
    }
  };

  const updateShopStatus = async (shopSeq) => {
    try {
      console.log("전송 데이터:", { shopSeq, shopApprStatus: 'Y' }); // 로그 추가
      const response = await axios.put(`http://localhost:8100/api/v1/admin/shop/${shopSeq}/auth`,
          'Y',{
            headers: {
              'Content-Type' : 'application/json'
            }
          });
      if (response.status === 200){
        alert("요청이 성공적으로 처리되었습니다.");
        // 상태 업데이트 후 데이터를 가져오거나 상태를 변경
        fetchShops();
      }
    }catch(error){
      alert("요청이 실패하였습니다.");
      console.error("상태 업데이트 도중 오류 발생 : ", error.response?.data || error);
    }
  };

  // 컴포넌트가 마운트될 때 데이터 요청
  onMounted(() => {
    fetchShops();
  });


  </script>

  <template>
    <div class="admin-container">
      <div class="header">
        <p class="title">관리자 페이지</p>
      </div>
      <div class="content-box">
        <AdminNav class="nav" />
        <div class="main-content">
          <AdminBodyTitle />
          <div class="content">
  <!--           매장 리스트 렌더링 -->
            <div class="shop-item" v-for="shop in shopList">
              <img :src= "shop.shopImgUrl" alt="매장 이미지" class="store-image" />
              <div class="shop-info">
                <p class="shop-name">{{ shop.shopName }}</p>
                <p class="shop-category">{{ shop.categoryName }}</p>
                <p class="shop-phone">전화 번호 | {{ shop.shopTel }}</p>
                <p class="shop-address">매장 주소 | {{ shop.shopAddress }}</p>
              </div>
              <button :class = "['status', shop.shopApprStatus === 'Y' ? 'status-Y' : 'status-N' ]"
              @click="shop.shopApprStatus === 'N' && updateShopStatus(shop.shopSeq)">
                {{ shop.shopApprStatus === 'Y' ? '처리 완료' : '처리 하기' }}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>

  <style scoped>
  .admin-container {
    display: flex;
    flex-direction: column;
    width: 90%;
    max-width: 1200px;
    margin: 60px auto;
    padding: 20px;
    background-color: white;
    border-radius: 10px;

  }

  .header {
    text-align: center;
    margin-bottom: 20px;
  }

  .title {
    font-size: 36px;
    font-weight: bold;
  }

  /* 메인 박스 스타일 */
  .content-box {
    display: flex;

    padding: 20px;
    border-radius: 10px;
  }

  /* 네비게이션 바 스타일 */
  .nav {
    width: 250px;
    padding: 96px 0 0 20px;

  }

  /* 메인 콘텐츠 스타일 */
  .main-content {
    flex: 1;
    padding: 0 0 5px 0;
    display: flex;
    flex-direction: column;
  }

  .content {
    display: flex;
    flex-direction: column;
    gap: 20px;
  }

  /* 매장 항목 스타일 */
  .shop-item {
    display: flex;
    align-items: center;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 10px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }

  .store-image {
    width: 133px;
    height: 133px;
    object-fit: cover;
    border-radius: 8px;
    margin-right: 20px;
  }

  .shop-info {
    flex: 1;
    display: flex;
    flex-direction: column;
  }
  .shop-name {
    font-size: 30px;
    font-weight: bold;
    margin: 0 0 4px 0;
  }

  .shop-category{
    font-size:20px;
    margin: 0 0 11.6px 0;
  }
  .shop-phone,
  .shop-address {
    font-size: 15px;
    margin: 0px 0 1px 0;
    color: #555;
  }

  .status {
    padding: 10px 20px;
    font-size: 14px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

  .status-N {
    color: white;
    background-color: #633A02;
  }

  .status-N:hover {
    background-color: #FFFFFF;
    border: 1px solid #633A02;
    color: #633A02;
  }

  .status-Y {
    color: black;
    cursor: default;
  }
  </style>
