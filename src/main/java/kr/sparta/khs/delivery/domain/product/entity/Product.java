package kr.sparta.khs.delivery.domain.product.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.common.entity.BaseEntity;
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
    private Integer price;

    @Column(name ="product_status", nullable = false)
    private String status;




    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;



}
