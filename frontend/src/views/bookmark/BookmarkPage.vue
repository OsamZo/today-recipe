<template>
  <div>
    <div v-if="isLoading" class="loading-message">
      데이터를 불러오는 중입니다...
    </div>
    <div v-else-if="bookmarks.length > 0" class="store-list-container">
      <h1 class="header-content">저장한 매장 리스트</h1>
      <div class="separator"></div>
      <div v-for="store in bookmarks" :key="store.bookmarkSeq" class="store-item">
        <img :src="store.shopImgUrl" alt="store image" class="store-image" />
        <div class="store-info">
          <h2>{{ store.shopName }}</h2>
          <p class="store-type">{{ store.categoryName }}</p>
          <p class="store-phone">전화번호 | {{ store.shopTel }}</p>
          <p>매장 주소 | {{ store.shopAddress }}</p>
        </div>
        <button @click="handleDeleteBookmark(store.bookmarkSeq)" class="bookmark-button">
          <font-awesome-icon icon="bookmark" />
        </button>
      </div>
    </div>
    <p v-else>저장된 북마크가 없습니다.</p>
  </div>
</template>

<script setup>
import { useBookmarkStore } from '@/store/BookmarkStore';
import { useUserStore } from '@/store/UserStore'; 
import { onMounted, ref } from 'vue';
import { storeToRefs } from 'pinia';

const bookmarkStore = useBookmarkStore();
const userStore = useUserStore();
const { bookmarks } = storeToRefs(bookmarkStore);
const isLoading = ref(true);
const isDeleting = ref(false);

onMounted(async () => {
  const userSeq = userStore.userSeq;
  if (!userSeq) {
    console.error('유효하지 않은 사용자 정보입니다.');
    return;
  }

  try {
    await bookmarkStore.loadBookmarks(userSeq);
  } catch (error) {
    console.error('북마크 로드 중 오류:', error);
  } finally {
    isLoading.value = false;
  }
});

const handleDeleteBookmark = async (bookmarkSeq) => {
  const userSeq = userStore.userSeq;
  if (!userSeq) {
    console.error('유효하지 않은 사용자 정보입니다.');
    return;
  }

  isDeleting.value = true;
  try {
    await bookmarkStore.removeBookmark(userSeq, bookmarkSeq);
    console.log('북마크가 성공적으로 삭제되었습니다.');
  } catch (error) {
    console.error('북마크 삭제 중 오류:', error);
  } finally {
    isDeleting.value = false;
  }
};
</script>

<style scoped>
.store-list-container {
  width: 80%;  
  max-width: 1200px;
  margin: 0 auto;
  margin-top: 5vw;
  text-align: center;
}

.header-content {
  margin-bottom: 2vw;
  font-size: 2.5vw; 
}

.separator {
  height: 0.3vw;
  background-color: #8B4513; 
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
  font-size: 2vw; 
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
  font-size: 2.5vw; 
}

.loading-message {
  text-align: center;
  font-size: 1.5vw;
  margin-top: 5vw;
}

@media (max-width: 768px) {
  .store-list-container {
    width: 90%; 
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
