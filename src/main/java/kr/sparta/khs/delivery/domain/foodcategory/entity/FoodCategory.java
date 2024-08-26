package kr.sparta.khs.delivery.domain.foodcategory.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.common.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.UUID;

@Entity(name = "p_foodcategory")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class FoodCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "food_category_id")
    private UUID id;


    @Column(name = "food_Type", nullable = false)
    private String foodType;


}
