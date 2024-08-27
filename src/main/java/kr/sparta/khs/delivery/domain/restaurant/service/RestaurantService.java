package kr.sparta.khs.delivery.domain.restaurant.service;

import kr.sparta.khs.delivery.domain.foodcategory.entity.FoodCategory;
import kr.sparta.khs.delivery.domain.foodcategory.repository.FoodCategoryRepository;
import kr.sparta.khs.delivery.domain.restaurant.entity.Restaurant;
import kr.sparta.khs.delivery.domain.restaurant.repository.RestaurantRepository;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.restaurant.dto.RestaurantCreateRequest;
import kr.sparta.khs.delivery.domain.restaurant.dto.RestaurantResponse;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class  RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final UserRepository userRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, FoodCategoryRepository foodCategoryRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.foodCategoryRepository = foodCategoryRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public void createRestaurant(RestaurantCreateRequest request) {

        FoodCategory foodCategory = foodCategoryRepository.findById(request.getFoodCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 음식 카테고리입니다."));

        SecurityUserDetails principal = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(principal.getSecurityUserInfo().getId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID입니다."));

        Restaurant restaurant = Restaurant.createRestaurant(request, foodCategory ,user);
        restaurantRepository.save(restaurant);

    }

    public RestaurantResponse getRestaurantById(UUID id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 레스토랑 ID입니다."));
        return RestaurantResponse.fromEntity(restaurant);
    }

    public List<RestaurantResponse> getAllRestaurants() {

        return restaurantRepository.findAll().stream().map(RestaurantResponse::fromEntity).collect(Collectors.toList());

    }

    public void updateRestaurant(UUID id) {
    }
}

