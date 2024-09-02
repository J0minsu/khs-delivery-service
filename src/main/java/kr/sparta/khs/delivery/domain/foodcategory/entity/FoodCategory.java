package kr.sparta.khs.delivery.domain.foodcategory.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.common.entity.BaseEntity;
import lombok.*;
import java.util.UUID;

@Entity(name = "p_foodCategory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FoodCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "food_category_id")
    private UUID id;

    @Column(name = "food_category_name", nullable = false)
    private String name;



    public static FoodCategory createFoodCategory(String name) {
        return new FoodCategory(UUID.randomUUID(), name);
    }

    public void updateName(String newName) {
        this.name = newName;
    }

}
