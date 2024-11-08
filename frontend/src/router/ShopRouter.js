import ShopDetailPage from "@/views/shop/ShopDetailPage.vue";
import ShopListPage from "@/views/shop/ShopListPage.vue";

export default [
    {
        path: '/category/:categorySeq/shop',
        component: ShopListPage,
    },
    {
        path: '/category/:categorySeq/shop/:shopSeq',
        component: ShopDetailPage,
    }
]