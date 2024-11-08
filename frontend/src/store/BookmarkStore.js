import { defineStore } from 'pinia';
import { fetchBookmarks } from '@/api/bookmark/bookmarkApi';

export const useBookmarkStore = defineStore('bookmark', {
  state: () => ({
    bookmarks: []
  }),
  actions: {
    async loadBookmarks(userSeq) {
        try {
          const response = await fetchBookmarks(userSeq);
          this.bookmarks = response.data; // 응답 데이터의 구조에 맞게 접근
        } catch (error) {
          console.error('북마크 데이터를 불러오는 중 오류:', error);
        }
      }
  }
});