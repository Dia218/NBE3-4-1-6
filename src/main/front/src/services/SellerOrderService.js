import axios from 'axios';

const API_URL = 'http://localhost:8080/admin/orders';

export const fetchOrders = async (page, size, emailFilter) => {
  try {
    // 기본 파라미터 설정
    const params = {
      page: page,
      size: size,
    };

    // emailFilter가 있을 경우에만 email 파라미터를 추가
    if (emailFilter) {
      params.email = emailFilter;
    }

    // API 요청 보내기
    const response = await axios.get(API_URL, { params });
    return response.data; // { items, currentPageNumber, totalPages, totalItems }
  } catch (error) {
    throw new Error('Error fetching orders:', error);
  }
};