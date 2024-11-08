import ShopListPage from "@/views/shop/ShopListPage.vue";

export default [
    {
        path: '/category/:categorySeq/shop',
        component: ShopListPage,
        props: true
    },
]