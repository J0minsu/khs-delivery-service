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

    public Product(String name, String description, int price, String status, Restaurant restaurant) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.restaurant = restaurant;
        this.restaurant.addProduct(this);
    }


    public static Product createProduct(ProductRequest productRequest, Restaurant restaurant) {
        return new Product(
                productRequest.getName(),
                productRequest.getDescription(),
                productRequest.getPrice(),
                productRequest.getStatus(),
                restaurant
        );
    }

    public void updateProduct(ProductRequest productRequest) {
        this.name = productRequest.getName();
        this.description = productRequest.getDescription();
        this.price = productRequest.getPrice();
        this.status = productRequest.getStatus();
    }
}
