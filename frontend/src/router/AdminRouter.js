import AdminShopAuth from "@/views/admin/AdminShopAuthSearch.vue";
import AdminShopSearch from "@/views/admin/AdminShopSearch.vue";
import AdminShopCategory from "@/views/admin/AdminShopCategory.vue";

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
    }
];