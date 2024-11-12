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
export const fetchBookList = async (userSeq) => {
  try {
    const response = await axios.get(`http://localhost:8100/api/v1/user/${userSeq}/book`);
    return response.data.data;
  } catch (error) {
    console.log("예약 내역 조회 중 오류 발생", error);
    return [];
  }
};

// 회원 정보 조회
export const fetchUserInfo = async (userSeq) => {
  try {
    const response = await axios.get(`http://localhost:8100/api/v1/user/${userSeq}/info`);
    return response.data.data;
  } catch (error) {
    console.log("회원 정보 조회 중 오류", error);
    return [];
  }
};

// 회원 이용 내역 조회
export const fetchUserHistory = async (userSeq) => {
  try {
    const response = await axios.get(`http://localhost:8100/api/v1/user/${userSeq}/history`);
    return response.data.data;
  } catch (error) {
    console.log("회원 이용 내역 조회 중 오류", error);
    return [];
  }
};

// 회원 정보 수정
export const updateUserInfo = async (userSeq, userNickname) => {
  try {
    console.log(userNickname);
    const response = await axios.put(`http://localhost:8100/api/v1/user/${userSeq}/info`, {
      userNickname,
    });
    return response.data;
  } catch (error) {
    console.log("회원 정보 수정 중 오류 발생", error);
    return [];
  }
};

// 회원 탈퇴
export const deleteUser = async (userSeq) => {
  try {
    const response = await axios.delete(`http://localhost:8100/api/v1/user/${userSeq}`);
    return response.data;
  } catch (error) {
    console.log("회원 탈퇴 도중 오류 발생", error);
    return [];
  }
};

// 로그아웃
export const logoutUser = async () => {
  try {
    const response = await axios.post('http://localhost:8100/api/v1/logout', {}, {
      withCredentials: true // 쿠키 포함 요청
    });
    return response.data;
  } catch (error) {
    console.error('로그아웃 중 오류 발생:', error);
    throw error;
  }
};
