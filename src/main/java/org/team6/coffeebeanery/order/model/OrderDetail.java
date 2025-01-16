package org.team6.coffeebeanery.order.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.team6.coffeebeanery.product.model.Product;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId; //주문 상세 ID

    @Column(nullable = false)
    private Integer productQuantity; //주문 상품 수량

    @Column(nullable = false)
    private Long orderPrice; //주문 시점 가격
    
    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    // 상세 주문이 상품을 참조하는데 상품이 삭제되면 db에서 참조하는 product_id값을 자동으로  null로 바꿔주지않는다고합니다.
    // DB에서 설정하거나 여기서 OnDelete 어노테이션을 사용해야되는거 같습니다.
    // 이 설정 없이 샘플 product 만들고 postman으로 DELETE /seller/products/1 하면 오류나옵니다.
    private Product product; //주문 상품
    
    @ManyToOne
    private Order order; //소속된 주문
}