# coffeebeanery frontend

> react

### 디렉터리 구조

```
│   App.css
│   App.js
│   index.css
│   index.js ### 리액트 애플리케이션 엔트리 포인트, 루트 컴포넌트 렌더링
│   reportWebVitals.js ### 애플리케이션 성능 측정 도구
│
├───components ### 재사용 가능한 UI 컴포넌트
│   └───productList
│           ListLayout.css
│           ListLayout.jsx
│           ProductBoxBase.css
│           ProductBoxBase.jsx
│           ProductBoxOption.css
│           ProductBoxOption.jsx
│
├───pages ### 각 페이지의 레이아웃
│   ├───Buyer
│   │       CartPage.jsx
│   │       EmailInputPage.jsx
│   │       OrderHistoryPage.jsx
│   │       PaymentCompletePage.jsx
│   │       ProductListPage.jsx
│   │       ShippingDetailPage.jsx
│   │
│   ├───Seller
│   │       OrderManagementPage.jsx
│   │       PasswordInputPage.jsx
│   │       ProductManagePage.jsx
│   │
│   └───Shared
│           ErrorPage.jsx
│
├───services ### 백엔드 API 호출 및 데이터 처리
│       BuyerOrderService.js
│       BuyerProductService.js
│       DeliveryService.js
│       SellerOrderService.js
│       SellerProductService.js
│
└───tests ### 테스트 파일
        App.test.js
        setupTests.js
```

<br>

---

### 별첨

[리액트 관련 글](https://velog.io/@dia218/add-React-to-IntelliJ-SpringBoot-exp)
