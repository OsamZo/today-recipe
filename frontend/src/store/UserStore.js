import { defineStore } from 'pinia';
import { loginWithGoogle, resultWithGoogle, submitNickname } from '@/api/user/UserApi';

export const useUserStore = defineStore('user', {
  state: () => ({
    jwtToken: null,
    nickname: '',
    userSeq: null
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
          this.storeJwtToken(result.token);
          window.location.href = result.status === 'signup' ? '/signup' : '/';
        } else {
          throw new Error('예상치 못한 응답 상태입니다.');
        }
      } catch (error) {
        alert('로그인 처리 중 오류 발생');
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
    storeJwtToken(token) {
      this.jwtToken = token;
      localStorage.setItem('jwtToken', token);

      try {
        const decodedToken = decodeJwt(token);
        this.userSeq = decodedToken.userSeq;
      } catch {
        alert('JWT 디코딩 중 오류가 발생했습니다.');
      }
    }
  }
});

function decodeJwt(token) {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    );
    return JSON.parse(jsonPayload);
  } catch {
    throw new Error('JWT 디코딩에 실패했습니다.');
  }
}
