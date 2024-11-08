<template>
  <div class="store-list-container">
    <h1 class="header-content">저장한 매장 리스트</h1>
    <div class="separator"></div>
    <div v-for="(store, index) in stores" :key="index" class="store-item">
      <img :src="store.image" alt="store image" class="store-image" />
      <div class="store-info">
        <h2>{{ store.name }}</h2>
        <p class="store-type">{{ store.type }}</p>
        <p class="store-phone">전화번호 | {{ store.phone }}</p>
        <p>매장 주소 | {{ store.address }}</p>
      </div>
      <button class="bookmark-button">
        <font-awesome-icon icon="bookmark" />
      </button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      stores: [
        {
          image: 'https://goruna.s3.us-west-1.amazonaws.com/480a8f33-beff-475f-8fd2-cc8173ede065_%EB%A3%A8%ED%94%BC.jpg',
          name: '볼피노',
          type: '양식',
          phone: '02-000-0000',
          address: '서울특별시 강남구 도산대로45길 10-7 신사동 K빌딩 1층'
        },
        // 여러 매장 데이터 추가 가능
      ]
    };
  }
};
</script>

<style scoped>
.store-list-container {
  width: 80%; /* 반응형 비율 조정 */
  max-width: 1200px;
  margin: 0 auto;
  margin-top: 5vw;
  text-align: center;
}

.header-content {
  margin-bottom: 2vw;
  font-size: 2.5vw; /* 반응형 폰트 크기 */
}

.separator {
  height: 0.4vw;
  background-color: #8B4513; /* 갈색 바 */
  margin-bottom: 3vw;
}

.store-item {
  display: flex;
  border-bottom: 0.3vw solid #ddd;
  padding: 1.5vw 0;
  align-items: center;
}

.store-image {
  width: 12vw;
  height: 12vw;
  max-width: 150px;
  max-height: 150px;
  margin-right: 2vw;
  margin-left: 2vw;
}

.store-info {
  flex-grow: 1;
  text-align: left;
}

.store-info h2 {
  font-size: 2vw; /* 반응형 폰트 크기 */
  margin-bottom: 1vw;
}

.store-phone {
  margin-bottom: 1vw;
}

.store-type {
  margin-bottom: 2vw;
}

.bookmark-button {
  background: none;
  border: none;
  cursor: pointer;
  margin-right: 2vw;
  font-size: 2.5vw; /* 반응형 아이콘 크기 */
}

@media (max-width: 768px) {
  .store-list-container {
    width: 90%; /* 모바일에서 더 넓게 */
  }
  .store-item {
    flex-direction: column;
    align-items: flex-start;
  }
  .store-image {
    margin-bottom: 1vw;
  }
}
</style>
