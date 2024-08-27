package kr.sparta.khs.delivery.domain.product.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.common.entity.BaseEntity;
import kr.sparta.khs.delivery.domain.product.dto.ProductRequest;
import kr.sparta.khs.delivery.domain.restaurant.entity.Restaurant;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity(name = "p_product")
@Getter
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID id;

    @Column(name ="product_name", nullable = false)
    private String name;

    @Column(name ="product_description", nullable = false)
    private String description;

    @Column(name ="product_price", nullable = false)
    private int price;

    @Column(name ="product_status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


    public static Product of(ProductRequest productRequest,Restaurant restaurant) {

        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .status(productRequest.getStatus())
                .restaurant(restaurant)
                .build();

    }
}
