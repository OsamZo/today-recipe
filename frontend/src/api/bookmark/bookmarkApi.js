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