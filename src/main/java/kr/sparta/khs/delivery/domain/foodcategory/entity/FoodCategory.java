package kr.sparta.khs.delivery.domain.foodcategory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "p_foodcategory")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class FoodCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "food_category_id")
    private UUID id;


    @Column(name = "food_Type")
    private String foodType;


}
