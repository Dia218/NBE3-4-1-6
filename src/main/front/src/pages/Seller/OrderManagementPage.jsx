import React, { useState, useEffect } from 'react';
import './OrderManagementPage.css';
import { fetchOrders } from '../../services/SellerOrderService';

const OrderManagementPage = () => {
  const [orders, setOrders] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [emailFilter, setEmailFilter] = useState('');  // 이메일 필터 상태 추가
  const pageSize = 10;

  // 주문 데이터 불러오기
  useEffect(() => {
    fetchOrders(currentPage - 1, pageSize, emailFilter)  // 이메일 필터 파라미터 추가
      .then((data) => {
        console.log(data);
        setOrders(data.items);
        setTotalPages(data.totalPages);
      })
      .catch((error) => {
        console.error('Error fetching orders:', error);
      });
  }, [currentPage, emailFilter]);  // 이메일 필터가 변경될 때마다 다시 요청

  // 페이지 변경 함수
  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  // 이메일 필터 변경 함수
  const handleEmailFilterChange = (event) => {
    setEmailFilter(event.target.value);
  };

  return (
    <div className="order-management-page">
      <h1>주문 관리 페이지</h1>

      {/* 이메일 입력 필드 추가 */}
      <div>
        <input
          type="email"
          placeholder="이메일로 주문 검색"
          value={emailFilter}
          onChange={handleEmailFilterChange}
        />
      </div>

      <table className="order-table">
        <thead>
          <tr>
            <th>주문 번호</th>
            <th>고객 이메일</th>
            <th>주소</th>
            <th>주문 날짜</th>
            <th>총 가격</th>
          </tr>
        </thead>
        <tbody>
          {orders.length === 0 ? (
            <tr>
              <td colSpan="5">주문 데이터가 없습니다.</td>
            </tr>
          ) : (
            orders.map(order => (
              <tr key={order.orderId}>
                <td>{order.orderId}</td>
                <td>{order.customerEmail}</td>
                <td>{order.address.baseAddress} {order.address.detailAddress} {order.address.zipCode}</td>
                <td>{new Date(order.orderCreatedAt).toLocaleString()}</td>
                <td>{order.totalPrice}원</td>
              </tr>
            ))
          )}
        </tbody>
      </table>

      {/* 페이지 네비게이션 */}
      <div className="pagination">
        <button
          onClick={() => handlePageChange(currentPage - 1)}
          disabled={currentPage === 1}
        >
          이전
        </button>
        <span>{currentPage} / {totalPages}</span>
        <button
          onClick={() => handlePageChange(currentPage + 1)}
          disabled={currentPage === totalPages}
        >
          다음
        </button>
      </div>
    </div>
  );
};

export default OrderManagementPage;
