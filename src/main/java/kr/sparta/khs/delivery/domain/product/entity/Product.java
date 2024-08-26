package kr.sparta.khs.delivery.domain.product.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.restaurant.entity.Restaurant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID id;

    private String name;
    private String description;
    private Double price;
    private String status;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;



}
