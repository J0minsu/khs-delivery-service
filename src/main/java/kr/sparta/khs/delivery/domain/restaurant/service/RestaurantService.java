package kr.sparta.khs.delivery.domain.restaurant.service;

import kr.sparta.khs.delivery.domain.foodcategory.entity.FoodCategory;
import kr.sparta.khs.delivery.domain.foodcategory.repository.FoodCategoryRepository;
import kr.sparta.khs.delivery.domain.restaurant.dto.UpdateRestaurantRequest;
import kr.sparta.khs.delivery.domain.restaurant.entity.Restaurant;
import kr.sparta.khs.delivery.domain.restaurant.repository.RestaurantRepository;
import kr.sparta.khs.delivery.domain.user.entity.AuthType;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.restaurant.dto.RestaurantCreateRequest;
import kr.sparta.khs.delivery.domain.restaurant.dto.RestaurantResponse;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final UserRepository userRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, FoodCategoryRepository foodCategoryRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.foodCategoryRepository = foodCategoryRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public void createRestaurant(RestaurantCreateRequest request, SecurityUserDetails userDetails) {

        int userId = userDetails.getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID 입니다."));

        FoodCategory foodCategory = foodCategoryRepository.findByName(request.getFoodCategoryName())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 음식 카테고리입니다."));

        restaurantRepository.save(Restaurant.createRestaurant(request, foodCategory, user));
    }

    @Transactional(readOnly = true)
    public RestaurantResponse getRestaurantById(UUID id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 레스토랑 ID 입니다."));
        return RestaurantResponse.fromEntity(restaurant);
    }

    @Transactional(readOnly = true)
    public Page<RestaurantResponse> getAllRestaurants(Pageable pageable) {
        return restaurantRepository.findAll(pageable)
                .map(RestaurantResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<RestaurantResponse> getSearchedRestaurants(String restaurantName, Pageable pageable) {

        return restaurantRepository.findByNameContainingIgnoreCase(restaurantName, pageable)
                .map(RestaurantResponse::fromEntity);
    }

    @Transactional
    public void updateRestaurant(UUID id, UpdateRestaurantRequest request, SecurityUserDetails userDetails) {

          int userId = userDetails.getId();

        FoodCategory foodCategory = foodCategoryRepository.findByName(request.getFoodCategoryName())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 음식 카테고리입니다."));

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지않는 레스토랑 입니다"));

        if (restaurant.getUser().getId() == userId || userDetails.getAuthType().equals(AuthType.MASTER)) {

            restaurant.updateRestaurant(request, foodCategory);
            restaurantRepository.save(restaurant);
        } else throw new IllegalArgumentException("레스토랑 수정 권한없음");



    }

    @Transactional
    public void deleteRestaurant(UUID id, SecurityUserDetails userDetails) {

        int userId = userDetails.getId();

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지않는 레스토랑 입니다"));

        if (restaurant.isDeleted())  throw new IllegalArgumentException("이미 삭제 처리된 레스토랑 입니다.");

        if (restaurant.getUser().getId() == userId || userDetails.getAuthType().equals(AuthType.MASTER)) {

            restaurant.delete(userId);
            restaurantRepository.save(restaurant);
        }else throw new IllegalArgumentException("레스토랑 수정 권한없음");


    }

}

