import axios from 'axios';

export const fetchBookmarks = async (userSeq) => {
  try {
    const response = await axios.get(`http://localhost:8100/api/v1/user/${userSeq}/bookmark`);
    return response.data;
  } catch (error) {
    console.error('API 호출 중 오류:', error);
    throw error;
  }
};

export const deleteBookmark = async (userSeq, bookmarkSeq) => {
    try {
      await axios.delete(`http://localhost:8100/api/v1/user/${userSeq}/bookmark/${bookmarkSeq}`);
      console.log('북마크가 성공적으로 삭제되었습니다.');
    } catch (error) {
      console.error('북마크 삭제 중 오류:', error);
      throw error;
    }
  };