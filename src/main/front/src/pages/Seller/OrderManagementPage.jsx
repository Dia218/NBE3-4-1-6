import React, { useState, useEffect } from 'react';
import './OrderManagementPage.css';
import { fetchOrders } from '../../services/SellerOrderService';

const OrderManagementPage = () => {
  const [orders, setOrders] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [emailFilter, setEmailFilter] = useState('');
  const [selectedOrder, setSelectedOrder] = useState(null); // 선택된 주문 상태 추가
  const pageSize = 10;

  useEffect(() => {
    fetchOrders(currentPage - 1, pageSize, emailFilter)
      .then((data) => {
        console.log(data);
        setOrders(data.items);
        setTotalPages(data.totalPages);
      })
      .catch((error) => {
        console.error('Error fetching orders:', error);
      });
  }, [currentPage, emailFilter]);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const handleEmailFilterChange = (event) => {
    setEmailFilter(event.target.value);
  };

  const handleOrderClick = (order) => {
    setSelectedOrder(order); // 주문 클릭 시 상세정보 설정
  };

  return (
    <div className="order-management-page">
      <button onClick={() => window.location.href = "/admin/products"}>상품 관리</button>

      <h1>주문 관리 페이지</h1>

      <div>
        <input
          type="email"
          placeholder="이메일로 주문 검색"
          value={emailFilter}
          onChange={handleEmailFilterChange}
        />
      </div>

      <div className="order-container">
        <table className="order-table">
          <thead>
            <tr>
              <th>고객 이메일</th>
              <th>주문 시간</th>
              <th>배송 상태</th>
              <th>총 금액</th>
              <th>상품 이미지</th>
              <th>상품명</th>
              <th>상품 금액</th>
              <th>주문 개수</th>
            </tr>
          </thead>
          <tbody>
            {orders.length === 0 ? (
              <tr>
                <td colSpan="8">주문 데이터가 없습니다.</td>
              </tr>
            ) : (
              orders.map(order => (
                <tr key={order.orderId} onClick={() => handleOrderClick(order)}>
                  <td>{order.customerEmail}</td>
                  <td>{new Date(order.orderCreatedAt).toLocaleString()}</td>
                  <td>
                    {/* orderStatus에 맞는 한글 상태 변환 */}
                    {order.orderStatus === 'ORDERED' ? '주문 완료' :
                     order.orderStatus === 'PREPARING' ? '배송 중' :
                     order.orderStatus === 'DELIVERED' ? '배송 완료' :
                     order.orderStatus === 'CANCELLED' ? '주문 취소' :
                     '상태 없음'}
                  </td>
                  <td>{order.totalPrice * order.quantity}원</td>
                  <td>
                    <img src={order.productImageUrl} alt={order.productName} className="product-image" />
                  </td>
                  <td>{order.productName}</td>
                  <td>{order.productPrice}원</td>
                  <td>{order.quantity}개</td>
                </tr>
              ))
            )}
          </tbody>
        </table>

        <div className="summary-box">
          {selectedOrder ? (
            <div>
              <h2>주문 상세</h2>
              <p>이메일: {selectedOrder.customerEmail}</p>
              <p>주문 시간: {new Date(selectedOrder.orderCreatedAt).toLocaleString()}</p>
              <p>주소: {selectedOrder.address.baseAddress} {selectedOrder.address.detailAddress} {selectedOrder.address.zipCode}</p>
              <p>우편번호: {selectedOrder.address.zipCode}</p>
              <p>배송 상태: {selectedOrder.orderStatus === 'ORDERED' ? '주문 완료' :
                                selectedOrder.orderStatus === 'PREPARING' ? '배송 중' :
                                selectedOrder.orderStatus === 'DELIVERED' ? '배송 완료' :
                                selectedOrder.orderStatus === 'CANCELLED' ? '주문 취소' : '상태 없음'}</p>
              <p>상품명: {selectedOrder.productName}</p>
              <p>상품 금액: {selectedOrder.productPrice}원</p>
              <p>주문 개수: {selectedOrder.quantity}개</p>
              <img src={selectedOrder.productImageUrl} alt={selectedOrder.productName} className="product-image" />
            </div>
          ) : (
            <div>
              <h2>주문 상세</h2>
              <p>주문을 클릭하면 상세 정보가 여기에 표시됩니다.</p>
            </div>
          )}
        </div>
      </div>

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
