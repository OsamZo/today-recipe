import axios from 'axios';

// 구글 로그인 URL 요청
export const loginWithGoogle = async () => {
  try {
    const response = await axios.post('http://localhost:8100/api/v1/oauth2/google');
    return response.data;
  } catch (error) {
    console.error('로그인 URL 요청 실패:', error);
    throw error;
  }
};

// 구글 인증 코드 처리
export const resultWithGoogle = async (authCode) => {
  try {
    const response = await axios.get(`http://localhost:8100/api/v1/oauth2/google?code=${authCode}`);
    return response.data;
  } catch (error) {
    console.error('로그인 처리 중 오류 발생', error);
    throw error;
  }
};

// 닉네임 추가
export const submitNickname = async (userSeq, nickname) => {
  try {
    const response = await axios.post(`http://localhost:8100/api/v1/user/${userSeq}/nickname`, {
      userNickname: nickname
    });
    return response.data;
  } catch (error) {
    console.error('닉네임 추가 실패', error);
    throw error;
  }
};

// 마이페이지 예약 내역 조회
export const fetchBookList = async(userSeq) => {
  try {
    const response = await axios.get(`http://localhost:8100/api/v1/user/${userSeq}/book`);
    return response.data.data;
  } catch (error) {
    console.log("예약 내역 조회", error);
    return [];
  }
}