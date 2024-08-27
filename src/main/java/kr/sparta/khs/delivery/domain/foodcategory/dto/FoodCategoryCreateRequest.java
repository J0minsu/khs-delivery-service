package kr.sparta.khs.delivery.domain.foodcategory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodCategoryCreateRequest {

    private String foodType;

}
