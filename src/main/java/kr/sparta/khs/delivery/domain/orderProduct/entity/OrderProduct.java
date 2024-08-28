package kr.sparta.khs.delivery.domain.orderProduct.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.common.entity.BaseEntity;
import kr.sparta.khs.delivery.domain.order.entity.Order;
import kr.sparta.khs.delivery.domain.product.entity.Product;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@ToString
@Table(name = "p_order_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_product_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "product_quantity", nullable = false)
    private Integer quantity;
    @Column(name = "product_price", nullable = false)
    private Integer price;
//    public static OrderProduct of(Order order, Product product, Integer quantity) {
//        OrderProduct orderProduct = new OrderProduct(order, product, quantity, product.getPrice());
//        return orderProduct;
//    }
    public OrderProduct(Order order, Product product, Integer quantity, Integer price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public void updatePrice(Integer price) {
        this.price = price;
    }
}
