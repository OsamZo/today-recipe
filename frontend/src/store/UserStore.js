import { defineStore } from 'pinia';
import { loginWithGoogle, resultWithGoogle, submitNickname } from '@/api/user/UserApi';

export const useUserStore = defineStore('user', {
  state: () => ({
    nickname: '',
    userSeq: localStorage.getItem('userSeq') || null // 초기화 시 로컬 스토리지에서 복원
  }),
  actions: {
    async handleGoogleLogin() {
      try {
        const loginUrl = await loginWithGoogle();
        window.location.href = loginUrl;
      } catch (error) {
        alert('로그인 URL 요청 실패');
      }
    },
    async processGoogleAuth(authCode) {
      try {
        const result = await resultWithGoogle(authCode);
        if (['signup', 'login'].includes(result.status)) {
          window.location.href = result.status === 'signup' ? '/signup' : '/';
        } else {
          throw new Error('예상치 못한 응답 상태입니다.');
        }
      } catch (error) {
        alert('로그인 처리 중 오류 발생');
      }
    },
    async restoreSession() {
      try {
        // 리프레시 토큰을 사용해 새로운 액세스 토큰 요청
        const response = await fetch('http://localhost:8100/api/v1/auth/refresh', {
          method: 'POST',
          credentials: 'include' // 쿠키 포함 요청
        });
    
        if (response.ok) {
          const data = await response.json();
          const newAccessToken = data.accessToken;
          this.userSeq = decodeJwt(newAccessToken).userSeq; // 토큰 디코딩하여 userSeq 설정
          localStorage.setItem('userSeq', this.userSeq); // 로컬 스토리지에 저장
          console.log('세션 복원 성공:', this.userSeq);
        } else {
          console.warn('리프레시 토큰으로 새 액세스 토큰 발급 실패');
          localStorage.removeItem('userSeq'); // 실패 시 로컬 스토리지에서 제거
        }
      } catch (error) {
        console.error('세션 복원 중 오류 발생:', error);
        localStorage.removeItem('userSeq'); // 오류 시 로컬 스토리지에서 제거
      }
    },
    async addNickname(userSeq, nickname) {
      try {
        if (typeof nickname !== 'string' || !nickname.trim()) {
          throw new Error('올바른 닉네임을 입력하세요.');
        }

        await submitNickname(userSeq, nickname);
        this.nickname = nickname;
      } catch (error) {
        throw error;
      }
    },
    clearUserSeq() {
      this.userSeq = null;
      localStorage.removeItem('userSeq'); // 로그아웃 시 로컬 스토리지에서 제거
    }
  }
});

function decodeJwt(token) {
  try {
    const base64Url = token.split('.')[1];
    if (!base64Url) throw new Error('JWT 구조가 잘못되었습니다.');
    
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    );
    return JSON.parse(jsonPayload);
  } catch (error) {
    console.error('JWT 디코딩 오류:', error);
    throw new Error('JWT 디코딩에 실패했습니다.');
  }
}
