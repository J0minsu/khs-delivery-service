package kr.sparta.khs.delivery.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.sparta.khs.delivery.config.holder.Result;
import kr.sparta.khs.delivery.domain.foodcategory.dto.UpdateFoodCategoryRequest;
import kr.sparta.khs.delivery.domain.foodcategory.service.FoodCategoryService;
import kr.sparta.khs.delivery.domain.foodcategory.dto.FoodCategoryCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "카테고리 API", description = "카테고리 관리 목적의 API Docs")
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@SecurityScheme( name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
@RequestMapping("/api/v1/foodCategories")
public class FoodCategoryController {

    private final FoodCategoryService foodCategoryService;

    public FoodCategoryController(FoodCategoryService foodCategoryService) {
        this.foodCategoryService = foodCategoryService;
    }


    @PostMapping
    @Secured("MASTER")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<?> createFoodCategory(
            @RequestBody FoodCategoryCreateRequest foodCategoryCreateRequest) {
            foodCategoryService.createFoodCategory(foodCategoryCreateRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(Result.success("음식 카테고리 생성완료."));
    }


    @GetMapping
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<?> getAllFoodCategory() {
         foodCategoryService.getAllFoodCategory();
         return ResponseEntity.status(HttpStatus.OK).body(foodCategoryService.getAllFoodCategory());
    }

    @PutMapping("{id}")
    @Secured("MASTER")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<?> updateFoodCategory(@PathVariable UUID id ,@RequestBody UpdateFoodCategoryRequest request) {
        foodCategoryService.updateFoodCategory(id,request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Result.success("음식 카테고리 수정완료"));
    }


}
