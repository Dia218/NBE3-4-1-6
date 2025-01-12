import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import OrderManagementPage from './pages/Seller/OrderManagementPage';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/admin/orders" element={<OrderManagementPage />} />
      </Routes>
    </Router>
  );
}

export default App;
