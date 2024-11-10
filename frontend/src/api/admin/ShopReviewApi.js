import { ref } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";

export function ShopReviewApi() {
    const restaurant = ref(null);
    const reviews = ref([]);
    const route = useRoute();

    const fetchReviews = async () => {
        const shopSeq = route.params.shopSeq;
        try {
            const response = await axios.get(`http://localhost:8100/api/v1/admin/${shopSeq}/review`);
            if (response.data.success) {
                const data = response.data.data;

                if (data.length > 0) {
                    restaurant.value = {
                        name: data[0].shopName,
                        image: data[0].shopImgUrl
                    };

                    reviews.value = data
                        .filter(review => review.reviewStatus === 'Y')
                        .map(review => ({
                            reviewSeq: review.reviewSeq,
                            userNickname: review.userNickname,
                            reviewContent: review.reviewContent
                        }));
                }
            } else {
                console.error("리뷰 데이터를 불러오는 데 실패했습니다:", response.data.message);
            }
        } catch (error) {
            console.error("리뷰 데이터를 불러오는 중 오류가 발생했습니다:", error);
        }
    };

    const deleteReview = async (index) => {
        const reviewSeq = reviews.value[index].reviewSeq;
        try {
            const response = await axios.delete(`http://localhost:8100/api/v1/admin/review/${reviewSeq}`);
            if (response.data.success) {
                reviews.value.splice(index, 1);
                alert("삭제가 완료되었습니다!");
            } else {
                console.error("리뷰 삭제에 실패했습니다.", response.data.message);
            }
        } catch (error) {
            console.error("리뷰 삭제 중 오류가 발생했습니다:", error);
        }
    };

    return { restaurant, reviews, fetchReviews, deleteReview };
}
