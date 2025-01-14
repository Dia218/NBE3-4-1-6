import React from 'react';
import './ProductBoxBase.css';

const ProductBoxBase = ({
                            image,
                            name,
                            description,
                            price,
                            additionalContent // ProductBoxOption
                        }) => {
    return (
        <div className="product-box">
            <div className="product-image-container">
                <img src={image} alt={name} className="product-image"/>
            </div>
            <div className="product-info">
                <h3 className="product-name">{name}</h3>
                <p className="product-description">{description}</p>
            </div>
            <div className="product-price">
                <p>{price}</p>
            </div>
            <div>
                {additionalContent && (
                    <div className="product-additional">{additionalContent}</div>
                )}
            </div>
        </div>
    );
};

export default ProductBoxBase;