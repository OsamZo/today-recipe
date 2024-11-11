<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';

const fileInputBusinessLicense = ref(null); // 사업자 등록증 파일 입력
const fileInputShopImg = ref(null); // 매장 사진 파일 입력
const businessLicenseFile = ref(null); // 사업자 등록증 파일
const shopImgFile = ref(null); // 매장 사진 파일
const openDayYear = ref('');
const openDayMonth = ref('');
const openDayDay = ref('');
const openDay = ref('');
const shopCategories = ref([]);
const selectedCategory = ref('');
const shopName = ref('');
const shopTel = ref('');
const shopAddress = ref('');
const shopIntroduction = ref('');

// 개업일 계산 (년, 월, 일이 변경될 때마다)
watch([openDayYear, openDayMonth, openDayDay], () => {
  if (openDayYear.value && openDayMonth.value && openDayDay.value) {
    openDay.value = `${openDayYear.value}-${String(openDayMonth.value).padStart(2, '0')}-${String(openDayDay.value).padStart(2, '0')}`;
  }
}, { immediate: true });

// 사업장 종류를 API에서 불러오는 함수
const fetchShopCategories = async () => {
  try {
    const response = await axios.get('http://localhost:8100/api/v1/category');
    shopCategories.value = response.data.data;
  } catch (error) {
    console.error('Error fetching shop categories:', error);
  }
};

// 파일 변경 핸들러
const handleBusinessLicenseFileChange = (event) => {
  businessLicenseFile.value = event.target.files[0];
};

const handleShopImgFileChange = (event) => {
  shopImgFile.value = event.target.files[0];
};

// 파일 입력 트리거
const triggerBusinessLicenseFileInput = () => {
  if (fileInputBusinessLicense.value) {
    fileInputBusinessLicense.value.click();
  }
};

const triggerShopImgFileInput = () => {
  if (fileInputShopImg.value) {
    fileInputShopImg.value.click();
  }
};

// 제출 버튼 클릭 시 실행될 함수
const submitShopInfo = async () => {
  const userSeq = localStorage.getItem("userSeq"); // 로컬 스토리지에서 userSeq 가져오기

  if (!selectedCategory.value) {
    alert('사업장 종류를 선택해주세요.');
    return;
  }

  // FormData 객체를 생성하여 파일과 데이터를 함께 전송
  const formData = new FormData();

  // 파일 추가
  formData.append('shopBusinessImgUrl', businessLicenseFile.value);
  formData.append('shopImgUrl', shopImgFile.value);

  const isoDate = new Date(openDay.value).toISOString(); // ISO 8601 형식으로 변환

  const data = {
    shopName: shopName.value,
    shopOpenDate: isoDate, // 변환된 ISO 형식 사용
    shopTel: shopTel.value,
    shopAddress: shopAddress.value,
    shopIntroduction: shopIntroduction.value
  };

// FormData에 추가
  const jsonData = JSON.stringify(data);
  formData.append('shopApplyReqDTO', jsonData);

  try {
    await axios.post(`http://localhost:8100/api/v1/user/${userSeq}/info/shop/${selectedCategory.value}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data' // 파일 업로드를 위해 multipart/form-data 설정
      }
    });
    alert('사업장 정보가 성공적으로 제출되었습니다.');
  } catch (error) {
    console.error('Error submitting shop info:', error);
    alert('제출 중 오류가 발생했습니다.');
  }
};

onMounted(() => {
  fetchShopCategories();
});
</script>

<template>
  <div class="container">
    <div class="header">
      매장 등록
    </div>
    <div class="body">
      <!-- 사업자 등록증 파일 입력 -->
      <div class="inputImg">
        <label for="business-license">사업자 등록증 </label>
        <input
            type="file"
            id="business-license"
            ref="fileInputBusinessLicense"
            @change="handleBusinessLicenseFileChange"
            style="display: none"
            accept="image/*"
        />
        <button @click="triggerBusinessLicenseFileInput" class="inputBtn">서류 첨부</button>
        <div v-if="businessLicenseFile" class="imgName">{{ businessLicenseFile.name }}</div>
      </div>

      <!-- 매장 사진 파일 입력 -->
      <div class="inputShopImg">
        <label for="shop-license">매장 사진</label>
        <input
            type="file"
            id="shop-img"
            ref="fileInputShopImg"
            @change="handleShopImgFileChange"
            style="display: none"
            accept="image/*"
        />
        <button @click="triggerShopImgFileInput" class="inputBtn">서류 첨부</button>
        <div v-if="shopImgFile" class="imgName">{{ shopImgFile.name }}</div>
      </div>

      <div class="name">
        <label for="shop-name">상호명 </label>
        <input id="shop-name" v-model="shopName" class="shop-name"/>
      </div>
      <div class="openday">
        <label for="open-day">개업일 </label>
        <div class="date-input">
          <input
              v-model="openDayYear"
              type="number"
              placeholder="YYYY"
              class="date-input-field"
              min="1900"
              max="2100"
          />
          <span>년</span>
          <input
              v-model="openDayMonth"
              type="number"
              placeholder="MM"
              class="date-input-field"
              min="1"
              max="12"
          />
          <span>월</span>
          <input
              v-model="openDayDay"
              type="number"
              placeholder="DD"
              class="date-input-field"
              min="1"
              max="31"
          />
          <span>일</span>
        </div>
      </div>
      <div class="tel">
        <label for="tel">매장 전화번호 </label>
        <input id="tel" v-model="shopTel" class="shop-name"/>
      </div>
      <div class="address">
        <label for="address">사업장 주소 </label>
        <input id="address" v-model="shopAddress" class="shop-name"/>
      </div>
      <div class="shopCategory">
        <label for="shop-category">사업장 종류 </label>
        <select v-model="selectedCategory" class="shop-name">
          <option value="" disabled selected>선택하세요</option>
          <option v-for="category in shopCategories" :key="category.categorySeq" :value="category.categorySeq">
            {{ category.categoryName }}
          </option>
        </select>
      </div>
      <div class="intro">
        <label for="intro">사업장 소개 </label>
        <textarea id="intro" v-model="shopIntroduction" class="shop-intro" />
      </div>
      <div class="foot">
        <button class="footBtn" @click="submitShopInfo">
          제출
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center; /* 중앙 정렬 */
  height: 100vh; /* 화면 전체 높이로 */
}

.header {
  margin: 0 0 92px 0; /* header와 body 사이에 40px 마진 */
  font-size: 40px;
  font-weight: bold;
}

.body {
  display: flex;
  flex-direction: column;
  gap: 34px; /* 바디 요소들 간 15px 간격 */
}

.body > div {
  display: flex;
  align-items: center;
  font-size: 25px;
}

.body label {
  margin-right: 40px;
  width: 180px;
  text-align: left;
}

.body .shop-name {
  padding: 5px;
  font-size: 20px; /* input 크기 맞추기 */
  width: 300px; /* input 너비 고정 */
  margin-left: auto; /* 입력 필드를 오른쪽으로 정렬 */
}

.date-input {
  display: flex;
  gap: 5px;
}

.date-input-field {
  padding: 5px;
  font-size: 20px;
  width: 70px;
}

.body button {
  margin-left: 4px;
  padding: 5px 10px;
  font-size: 20px;
}

.imgName {
  font-size: 20px;
  width: 140px;
  height: 40px;
  padding: 10px 0 0 10px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

span{
  padding: 4px 0 0 0;
}

.shop-intro {
  width: 303px;
  height: 136px;
  font-size: 21px;
  resize: none; /* 사용자가 크기 조절을 하지 못하게 설정 */
  overflow-wrap: break-word; /* 단어가 길어질 경우 줄바꿈 */
  border: 1px solid #ccc; /* 테두리 스타일 */
  border-radius: 4px; /* 모서리를 둥글게 설정 */
}

input {
  width: 360px;
  height: 44px;
  box-sizing: border-box; /* 요소 전체 너비에 padding과 border를 포함시킴 */
  padding: 8px; /* 필요에 따라 내부 여백 추가 */
  border: 1px solid #ccc; /* 테두리 스타일 */
  border-radius: 4px; /* 모서리를 둥글게 설정 */
}

select{
  box-sizing: border-box; /* 요소 전체 너비에 padding과 border를 포함시킴 */
  padding: 8px; /* 필요에 따라 내부 여백 추가 */
  border: 1px solid #ccc; /* 테두리 스타일 */
  border-radius: 4px; /* 모서리를 둥글게 설정 */
}

.inputBtn{
  border-radius: 4px; /* 모서리를 둥글게 설정 */
  border: 1px solid #633A02; /* 테두리 스타일 */
  background: #633A02;
  color:white;
  font-size: 16px !important;
  width: 153px;
  height: 44px;
  margin:0 0 0 0;
  cursor: pointer;
}

.foot{
  display: flex;
  justify-content: center;
}

.footBtn {
  border-radius: 4px;
  width: 326px;
  height: 44px;
  border: 1px solid #633A02; /* 테두리 스타일 */
  background: #633A02;
  color: white;
  font-size: 16px !important;
  cursor: pointer;
}
</style>
