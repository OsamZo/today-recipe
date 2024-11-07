import ReviewReadPage from "@/views/review/ReviewReadPage.vue";
import ReviewCreatePage from "@/views/review/ReviewCreatePage.vue";

export default [
    {
        path: '/review',
        component: ReviewReadPage
    },
    {
        path: '/review/add', 
        component: ReviewCreatePage
    }
];