import axios from 'axios';

export function authBtnApi(shopSeq, fetchShops){

    const acceptAuth = async () => {
        try {
            const response = await axios.put(
                `http://localhost:8100/api/v1/admin/shop/${shopSeq}/auth`,
                'APPROVE',
                {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
            );
            if (response.status === 200) {
                alert("요청이 성공적으로 처리되었습니다.");
                fetchShops();
            }
        } catch (error) {
            alert("요청이 실패하였습니다.");
            console.error("상태 업데이트 도중 오류 발생 : ", error.response?.data || error);
        }
    };

    const cancelAuth = async () => {
        try {
            const response = await axios.put(
                `http://localhost:8100/api/v1/admin/shop/${shopSeq}/auth`,
                'REJECT',
                {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
            );
            if (response.status === 200) {
                alert("요청이 성공적으로 처리되었습니다.");
                fetchShops();
            }
        } catch (error) {
            alert("요청이 실패하였습니다.");
            console.error("상태 업데이트 도중 오류 발생 : ", error.response?.data || error);
        }
    };

    return {
        acceptAuth,
        cancelAuth
    };
}

