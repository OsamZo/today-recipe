import AdminShopAuth from "@/views/admin/AdminShopAuthSearch.vue";
import AdminShopSearch from "@/views/admin/AdminShopSearch.vue";
import AdminShopCategory from "@/views/admin/AdminShopCategory.vue";
import AdminShopReview from "@/views/admin/AdminShopReview.vue";
import AdminShopAuthDetail from "@/views/admin/AdminShopAuthDetail.vue";

export default [
    {
        path: '/admin/shopAuth',
        component: AdminShopAuth
    },
    {
        path: '/admin/shopList',
        component: AdminShopSearch
    },
    {
        path: '/admin/shopCategory',
        component: AdminShopCategory
    },
    {
        path: '/admin/shopList/review/:shopSeq',
        component: AdminShopReview
    },
    {
        path: '/admin/shopAuth/:shopSeq/auth',
        component: AdminShopAuthDetail
    }
];