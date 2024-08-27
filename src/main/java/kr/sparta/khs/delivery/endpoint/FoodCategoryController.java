package kr.sparta.khs.delivery.endpoint;

import kr.sparta.khs.delivery.domain.foodcategory.service.FoodCategoryService;
import kr.sparta.khs.delivery.domain.foodcategory.dto.FoodCategoryCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foodcategory")
public class FoodCategoryController {

    private final FoodCategoryService foodCategoryService;

    public FoodCategoryController(FoodCategoryService foodCategoryService) {
        this.foodCategoryService = foodCategoryService;
    }


    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> createFoodCategory(@RequestBody FoodCategoryCreateRequest foodCategoryCreateRequest) {
        foodCategoryService.createFoodCategory(foodCategoryCreateRequest);
        return ResponseEntity.status(201).body("Food category created successfully");
    }









}
