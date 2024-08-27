package kr.sparta.khs.delivery.domain.foodcategory.service;

import kr.sparta.khs.delivery.domain.foodcategory.entity.FoodCategory;
import kr.sparta.khs.delivery.domain.foodcategory.repository.FoodCategoryRepository;
import kr.sparta.khs.delivery.domain.foodcategory.dto.FoodCategoryCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;

    public FoodCategoryService(FoodCategoryRepository foodCategoryRepository) {
        this.foodCategoryRepository = foodCategoryRepository;
    }


    public void createFoodCategory(FoodCategoryCreateRequest foodCategoryCreateRequest) {
        FoodCategory foodCategory = FoodCategory.of(foodCategoryCreateRequest.getFoodType());
        foodCategoryRepository.save(foodCategory);
    }


}
