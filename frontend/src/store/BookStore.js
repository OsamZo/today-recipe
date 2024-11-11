import {defineStore} from "pinia";
import {fetchOwnerShopBooks} from "@/api/shop/BookApi.js";

export const useBookStore = defineStore('bookStore', {
   state: () => ({
       bookSeqs: [],
       ownerShopBooks: JSON.parse(localStorage.getItem('ownerShopBooks')) || [],
       bookIsProductReceived: ''
   }),

    actions: {
       async loadReceivedBooks(userSeq) {
           try {
               const bookList = await fetchOwnerShopBooks(userSeq);
               console.log(bookList); // 로그 정상
               if (bookList && bookList.length > 0) {
                   this.ownerShopBooks = bookList.map(book => book.bookSeq);
                   localStorage.setItem('bookSeq', JSON.stringify(this.bookSeqs));
                   // console.log(localStorage.getItem('bookSeq')); // 로그 정상
                   return bookList;
               }
           } catch (error) {
               console.log('스토어에서 예약 데이터를 로드하는 중 오류 발생 :', error);
           }
       },

       async updateReceivedStatus(bookSeq, updateBookStatus) {
           try {
               await fetchOwnerShopBooks(bookSeq, updateBookStatus);
           } catch (error) {
               console.log('스토어에서 예약 정보를 수정하는 중 오류 발생 :', error);
           }
       },

    }
});