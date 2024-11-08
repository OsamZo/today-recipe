import { defineStore } from 'pinia';
import { fetchBookmarks, deleteBookmark } from '@/api/bookmark/bookmarkApi'; 

export const useBookmarkStore = defineStore('bookmark', {
  state: () => ({
    bookmarks: []
  }),
  actions: {
    async loadBookmarks(userSeq) {
      try {
        const response = await fetchBookmarks(userSeq);
        this.bookmarks = response.data; 
      } catch (error) {
        console.error('북마크 데이터를 불러오는 중 오류:', error);
      }
    },
    async removeBookmark(userSeq, bookmarkSeq) {
      try {
        await deleteBookmark(userSeq, bookmarkSeq); 
        this.bookmarks = this.bookmarks.filter(bookmark => bookmark.bookmarkSeq !== bookmarkSeq);
        console.log('북마크가 성공적으로 삭제되었습니다.');
      } catch (error) {
        console.error('북마크 삭제 중 오류:', error);
      }
    }
  }
});
