package kr.sparta.khs.delivery.domain.restaurant.service;

import kr.sparta.khs.delivery.domain.foodcategory.entity.FoodCategory;
import kr.sparta.khs.delivery.domain.foodcategory.repository.FoodCategoryRepository;
import kr.sparta.khs.delivery.domain.product.dto.ProductResponse;
import kr.sparta.khs.delivery.domain.restaurant.entity.Restaurant;
import kr.sparta.khs.delivery.domain.restaurant.repository.RestaurantRepository;
import kr.sparta.khs.delivery.domain.user.entity.AuthType;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.restaurant.dto.RestaurantCreateRequest;
import kr.sparta.khs.delivery.domain.restaurant.dto.RestaurantResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final FoodCategoryRepository foodCategoryRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, FoodCategoryRepository foodCategoryRepository) {
        this.restaurantRepository = restaurantRepository;
        this.foodCategoryRepository = foodCategoryRepository;
    }


    //todo auth 완료된 후 user와 맵핑 구현해야함.
    @Transactional
    public void createRestaurant(RestaurantCreateRequest request) {


        FoodCategory foodCategory = foodCategoryRepository.findById(request.getGetFoodCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 음식 카테고리입니다."));

        // 임시
        User user = User.createUser("a","a","a","a","a","a", AuthType.ADMIN,true);

        Restaurant restaurant = Restaurant.of(request,foodCategory ,user);

        restaurantRepository.save(restaurant);
    }

    public RestaurantResponse getRestaurantById(UUID id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 레스토랑 ID입니다."));
        return toResponse(restaurant);
    }

    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    private RestaurantResponse toResponse(Restaurant restaurant) {
        List<ProductResponse> productResponses = restaurant.getProducts().stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .status(product.getStatus())
                        .build())
                .collect(Collectors.toList());

        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getPhone(),
                restaurant.getMinPrice(),
                restaurant.getOperationHours(),
                restaurant.getClosedDays(),
                restaurant.getDeliveryTip(),
                restaurant.getStatus(),
                restaurant.getFoodCategory().getFoodType(),
                productResponses,
                restaurant.getUser().getName()
        );
    }

}
