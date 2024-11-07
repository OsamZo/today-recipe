import UserMyPage from "@/views/user/UserMyPage.vue";
import LoginPage from "@/views/user/LoginPage.vue";

export default [
    {
        path: '/user',
        component: UserMyPage
    },
    {
        path: '/login',
        component: LoginPage
    }
];