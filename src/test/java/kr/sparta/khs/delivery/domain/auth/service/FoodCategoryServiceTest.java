package kr.sparta.khs.delivery.domain.foodcategory.service;

import kr.sparta.khs.delivery.domain.foodcategory.dto.FoodCategoryCreateRequest;
import kr.sparta.khs.delivery.domain.foodcategory.dto.FoodCategoryResponse;
import kr.sparta.khs.delivery.domain.foodcategory.entity.FoodCategory;
import kr.sparta.khs.delivery.domain.foodcategory.repository.FoodCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FoodCategoryServiceTest {

    @Mock
    private FoodCategoryRepository foodCategoryRepository;
    @InjectMocks
    private FoodCategoryService foodCategoryService;

    @Test
    void createFoodCategorySuccess() {
        // Given
        FoodCategoryCreateRequest request = new FoodCategoryCreateRequest();
        request.setName("Italian");
        when(foodCategoryRepository.findByName("한식")).thenReturn(Optional.empty());
        // When
        foodCategoryService.createFoodCategory(request);
        // Then
        verify(foodCategoryRepository, times(1)).save(any(FoodCategory.class));
    }

    @Test
    void createFoodCategory_NameIsNull() {
        // Given
        FoodCategoryCreateRequest request = new FoodCategoryCreateRequest();
        request.setName(null);

        // When & Then
        assertThatThrownBy(() -> foodCategoryService.createFoodCategory(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("카테고리 이름을 입력해주세요.");
    }

    @Test
    void createFoodCategory_NameIsEmpty() {
        // Given
        FoodCategoryCreateRequest request = new FoodCategoryCreateRequest();
        request.setName("");
        // When & Then
        assertThatThrownBy(() -> foodCategoryService.createFoodCategory(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("카테고리 이름을 입력해주세요.");
    }

    @Test
    void createFoodCategory_NameExists() {
        // Given
        FoodCategoryCreateRequest request = new FoodCategoryCreateRequest();
        request.setName("ExistingCategory");
        when(foodCategoryRepository.findByName("ExistingCategory")).thenReturn(Optional.of(new FoodCategory()));
        // When & Then
        assertThatThrownBy(() -> foodCategoryService.createFoodCategory(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ExistingCategory는 이미 존재하는 카테고리 이름입니다");
    }


    @Test
    void getAllFoodCategory_ReturnList() {
        // Given
        FoodCategory foodCategory1 = new FoodCategory(UUID.randomUUID(), "한식");
        FoodCategory foodCategory2 = new FoodCategory(UUID.randomUUID(), "중식");
        List<FoodCategory> foodCategoryList = Arrays.asList(foodCategory1, foodCategory2);
        when(foodCategoryRepository.findAll()).thenReturn(foodCategoryList);
        // When
        List<FoodCategoryResponse> responseList = foodCategoryService.getAllFoodCategory();
        System.out.println("responseList = " + responseList);
        // Then
        assertThat(responseList).hasSize(2);
        assertThat(responseList.get(0).getId()).isEqualTo(foodCategory1.getId());
        assertThat(responseList.get(0).getName()).isEqualTo("한식");
        assertThat(responseList.get(1).getId()).isEqualTo(foodCategory2.getId());
        assertThat(responseList.get(1).getName()).isEqualTo("중식");
    }

    @Test
    void getAllFoodCategory_EmptyList_ReturnsEmptyList() {
        // Given
        when(foodCategoryRepository.findAll()).thenReturn(List.of());
        // When
        List<FoodCategoryResponse> responseList = foodCategoryService.getAllFoodCategory();
        // Then
        assertThat(responseList).isEmpty();
    }

}
