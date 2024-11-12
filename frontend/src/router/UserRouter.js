import UserMyPage from "@/views/user/UserMyPage.vue";
import LoginPage from "@/views/user/LoginPage.vue";
import LoginUserNickName from "@/views/user/LoginUserNickName.vue";
import MyPageBook from "@/views/user/MyPageBook.vue";
import UserShopApply from "@/views/user/UserShopApply.vue";
import MyPageHistory from "@/views/user/MyPageHistory.vue";
import MyPageReview from "@/views/user/MyPageReview.vue";
import MyPageUserInfo from "@/views/user/MyPageUserInfo.vue";
import { useUserStore } from '@/store/UserStore';
import { logoutUser } from '@/api/user/UserApi'; // logoutUser 함수 import

export default [
    {
        path: '/user',
        component: MyPageUserInfo
    },
    {
        path: '/login',
        component: LoginPage,
        beforeEnter: async (to, from, next) => {
            const userStore = useUserStore();
            try {
                // 백엔드의 /logout 엔드포인트 호출
                await logoutUser();
                userStore.clearUserSeq(); // 클라이언트 상태 초기화
                console.log('자동 로그아웃 실행');
            } catch (error) {
                console.error('자동 로그아웃 실패:', error);
            }
            next(); // 페이지 이동 허용
        }
    },
    {
        path: '/nickname',
        component: LoginUserNickName
    },
    {
        path: '/book',
        component: MyPageBook
    },
    {
        path: '/user/shop/apply',
        component: UserShopApply
    },
    {
        path: '/history',
        component: MyPageHistory
    },
    {
        path: '/user/review',
        component: MyPageReview
    }
];
