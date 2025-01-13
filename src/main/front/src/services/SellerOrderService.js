import axios from 'axios';

const API_URL = 'http://localhost:8080/admin/orders';

export const fetchOrders = async (page, size, emailFilter) => {
  try {
    const params = {
      page: page,
      size: size,
    };

    if (emailFilter) {
      params.email = emailFilter;
    }

    const response = await axios.get(API_URL, { params });
    return response.data; // { items, currentPageNumber, totalPages, totalItems }
  } catch (error) {
    throw new Error('Error fetching orders:', error);
  }
};
