import OwnerMyPage from "@/views/owner/OwnerMyPage.vue";

export default [
    {
        path: '/owner',
        component: OwnerMyPage,
        children: [
            { path: 'book', component: () => import("@/views/owner/OwnerMyPageBook.vue") },
            { path: 'history', component: () => import("@/views/owner/OwnerMyPageHistory.vue") },
            { path: 'review', component: () => import("@/views/owner/OwnerMyPageReview.vue") },
        ]
    }
];

