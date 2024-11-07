import AdminShopAuth from "@/views/admin/AdminShopAuthSearch.vue";
import AdminShopSearch from "@/views/admin/AdminShopSearch.vue";
export default [
    {
        path: '/admin/shopAuth',
        component: AdminShopAuth
    },
    {
        path: '/admin/shopList',
        component: AdminShopSearch
    }
];