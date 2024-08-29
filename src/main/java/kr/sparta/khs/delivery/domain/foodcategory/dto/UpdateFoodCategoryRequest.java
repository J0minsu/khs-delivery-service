package kr.sparta.khs.delivery.domain.foodcategory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFoodCategoryRequest {

    private String name;

}
