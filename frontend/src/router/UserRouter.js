import UserMyPage from "@/views/user/UserMyPage.vue";
import LoginPage from "@/views/user/LoginPage.vue";
import LoginUserNickName from "@/views/user/LoginUserNickName.vue";

export default [
    {
        path: '/user',
        component: UserMyPage
    },
    {
        path: '/login',
        component: LoginPage
    },
    {
        path: '/nickname',
        component: LoginUserNickName
    }
];