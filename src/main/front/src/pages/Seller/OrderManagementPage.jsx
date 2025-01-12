import React, { useState, useEffect } from 'react';
import './OrderManagementPage.css'; // 스타일 파일
import { fetchOrders } from '../../services/SellerOrderService'; // 수정된 경로로 import

const OrderManagementPage = () => {
  const [orders, setOrders] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);  // 현재 페이지 상태
  const [totalPages, setTotalPages] = useState(1);   // 전체 페이지 수
  const pageSize = 10;  // 페이지 크기 (한 페이지에 보여줄 주문 개수)

  // 주문 데이터 불러오기
  useEffect(() => {
    fetchOrders(currentPage - 1, pageSize)  // 페이지는 0부터 시작하므로 currentPage - 1
      .then((data) => {
        console.log(data); // 응답 구조 확인
        setOrders(data.items);
        setTotalPages(data.totalPages);
      })
      .catch((error) => {
        console.error('Error fetching orders:', error);
      });
  }, [currentPage]); // currentPage가 변경될 때마다 데이터를 새로 가져옴

  // 페이지 변경 함수
  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  return (
    <div className="order-management-page">
      <h1>주문 관리 페이지</h1>
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
