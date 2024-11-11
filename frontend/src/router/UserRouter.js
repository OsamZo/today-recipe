import UserMyPage from "@/views/user/UserMyPage.vue";
import LoginPage from "@/views/user/LoginPage.vue";
import LoginUserNickName from "@/views/user/LoginUserNickName.vue";
import MyPageBook from "@/views/user/MyPageBook.vue";
import UserShopApply from "@/views/user/UserShopApply.vue";
import MyPageHistory from "@/views/user/MyPageHistory.vue";
import MyPageReview from "@/views/user/MyPageReview.vue";

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
    },
    {
        path: '/book',
        component: MyPageBook
    },
    {
        path:'/user/shop/apply',
        component: UserShopApply
        path: '/history',
        component: MyPageHistory
    },
    {
        path: '/user/review',
        component: MyPageReview
    }
];