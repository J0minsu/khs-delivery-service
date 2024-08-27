package kr.sparta.khs.delivery.domain.foodcategory.service;


import kr.sparta.khs.delivery.domain.foodcategory.dto.FoodCategoryResponse;
import kr.sparta.khs.delivery.domain.foodcategory.dto.UpdateFoodCategoryRequest;
import kr.sparta.khs.delivery.domain.foodcategory.entity.FoodCategory;
import kr.sparta.khs.delivery.domain.foodcategory.repository.FoodCategoryRepository;
import kr.sparta.khs.delivery.domain.foodcategory.dto.FoodCategoryCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;

    public FoodCategoryService(FoodCategoryRepository foodCategoryRepository) {
        this.foodCategoryRepository = foodCategoryRepository;
    }


    @Transactional
    public void createFoodCategory(FoodCategoryCreateRequest foodCategoryCreateRequest) {

        String name = foodCategoryCreateRequest.getName();

        if (name ==null || name.isEmpty()) {
            throw new IllegalArgumentException("카테고리 이름을 입력해주세요.");
        }else if (foodCategoryRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException(name+"는 이미 존재하는 카테고리 이름입니다");
        }

        FoodCategory foodCategory = FoodCategory.createFoodCategory(foodCategoryCreateRequest.getName());

        foodCategoryRepository.save(foodCategory);
    }

    @Transactional(readOnly = true)
    public List<FoodCategoryResponse> getAllFoodCategory() {

        List<FoodCategory> foodCategoryList = foodCategoryRepository.findAll();
        return  foodCategoryList.stream().map(
                foodCategory -> new FoodCategoryResponse(foodCategory.getId(),foodCategory.getName())).toList();

    }


    @Transactional
    public void updateFoodCategory(UpdateFoodCategoryRequest request) {

        Optional<FoodCategory> optionalFoodCategory  = foodCategoryRepository.findById(request.getId());

        if (optionalFoodCategory.isEmpty()) {
            throw new IllegalArgumentException("ID의 음식 카테고리가 존재하지 않습니다");
        }
        String newName = request.getName();
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("새 카테고리 이름을 입력해주세요");
        }
        FoodCategory foodCategory = optionalFoodCategory.get();
        foodCategory.updateName(newName);
        foodCategoryRepository.save(foodCategory);

    }

    @Transactional
    public void deleteFoodCategory(UUID id) {
        Optional<FoodCategory> optionalFoodCategory  = foodCategoryRepository.findById(id);
        if (optionalFoodCategory.isEmpty()) {
            
        }
    }

}
