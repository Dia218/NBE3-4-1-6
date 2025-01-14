import React, {useEffect, useState} from 'react';
import './ProductBoxOption.css';

// 페이지에 따라 구성 요소 변경
const ProductBoxOption = ({pageType, initialQuantity = 1}) => {
    const [quantity, setQuantity] = useState(initialQuantity); // 초기 수량 값 설정

    // 수량 변경 핸들러
    const handleQuantityChange = (e) => {
        const newQuantity = Math.max(1, parseInt(e.target.value)); // 최소 수량 1
        setQuantity(newQuantity);
    };

    useEffect(() => {
        if (pageType === 'ChangeCart') {
            setQuantity(initialQuantity);
        }
    }, [pageType, initialQuantity]);

    const renderOptions = () => {
        switch (pageType) {
            case 'AddCart':
                return (
                    <div className="buttons-container">
                        <input // 수량 입력
                            type="number"
                            value={quantity}
                            onChange={handleQuantityChange}
                            className="quantity-input"
                        />
                        <button className="add-button">장바구니 추가</button>
                    </div>
                );

            case 'ChangeCart':
                return (
                    <div className="buttons-container">
                        <input // 수량 입력
                            type="number"
                            value={quantity}
                            onChange={handleQuantityChange}
                            className="quantity-input"
                        />
                        <button className="add-button">적용</button>
                        <button className="delete-button">삭제</button>
                    </div>
                );

            default:
                return (
                    <div className="buttons-container">
                        <div className="quantity-box">{quantity}</div>
                    </div>
                );
        }
    };
    return <div>{renderOptions()}</div>;
};

export default ProductBoxOption;