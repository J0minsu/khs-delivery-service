package kr.sparta.khs.delivery.domain.orderProduct.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.order.entity.Order;
import kr.sparta.khs.delivery.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@ToString
@Table(name = "p_order_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {
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
}
