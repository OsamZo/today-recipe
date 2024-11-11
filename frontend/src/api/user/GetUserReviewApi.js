import axios from 'axios';

const API_BASE_URL = 'http://localhost:8100/api/v1';

export const getUserReviews = async (userSeq) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/user/${userSeq}/review`);
        return response.data;
    } catch (error) {
        console.error("Error fetching reviews:", error);
        throw error;
    }
};
