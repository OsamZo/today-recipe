import { ref } from "vue";
import axios from "axios";

export function categoryApi() {
    const categoryList = ref([]);
    const selectedCategory = ref(null);
    const showModal = ref(false);

    const confirmDeleteCategory = (category) => {
        selectedCategory.value = category;
        showModal.value = true;
    };
    const fetchCategory = async () => {
        try {
            const response = await axios.get('http://localhost:8100/api/v1/admin/category');
            categoryList.value = response.data.data;
        } catch (error) {
            console.error("데이터를 불러오는 중 오류 발생", error);
        }
    };

    const deleteCategory = async () => {
        try {
            const response = await axios.delete(`http://localhost:8100/api/v1/admin/category/${selectedCategory.value.categorySeq}`);
            if (response.status === 200) {
                alert(`${selectedCategory.value.categoryName}가 삭제되었습니다.`);
                showModal.value=false;
                fetchCategory();
            }
        } catch (error) {
            alert("삭제 요청이 실패하였습니다.");
            console.error("카테고리 삭제 도중 오류 발생:", error.response?.data || error);
        }
    };

    return { categoryList, selectedCategory, showModal, confirmDeleteCategory, fetchCategory, deleteCategory };
}
