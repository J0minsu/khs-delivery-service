package kr.sparta.khs.delivery.endpoint;

import kr.sparta.khs.delivery.domain.foodcategory.dto.UpdateFoodCategoryRequest;
import kr.sparta.khs.delivery.domain.foodcategory.service.FoodCategoryService;
import kr.sparta.khs.delivery.domain.foodcategory.dto.FoodCategoryCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/foodCategories")
public class FoodCategoryController {

    private final FoodCategoryService foodCategoryService;

    public FoodCategoryController(FoodCategoryService foodCategoryService) {
        this.foodCategoryService = foodCategoryService;
    }


    @PostMapping
    @Secured("MASTER")
    public ResponseEntity<?> createFoodCategory(
            @RequestBody FoodCategoryCreateRequest foodCategoryCreateRequest) {
            foodCategoryService.createFoodCategory(foodCategoryCreateRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("음식 카테고리 생성완료.");
    }


    @GetMapping
    public ResponseEntity<?> getAllFoodCategory() {

         foodCategoryService.getAllFoodCategory();
         return ResponseEntity.status(HttpStatus.OK).body(foodCategoryService.getAllFoodCategory());
    }

    @PutMapping("{id}")
    @Secured("MASTER")
    public ResponseEntity<?> updateFoodCategory(@PathVariable UUID id ,@RequestBody UpdateFoodCategoryRequest request) {
        foodCategoryService.updateFoodCategory(id,request);
        return ResponseEntity.status(HttpStatus.OK).body("음식 카테고리 수정완료");
    }


}
