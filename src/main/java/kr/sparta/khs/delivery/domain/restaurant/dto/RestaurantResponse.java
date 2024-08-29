package kr.sparta.khs.delivery.domain.restaurant.dto;

import kr.sparta.khs.delivery.domain.product.dto.ProductResponse;
import kr.sparta.khs.delivery.domain.restaurant.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponse {
    private UUID id;
    private String name;
    private String address;
    private String phone;
    private int minPrice;
    private String operationHours;
    private String closedDays;
    private String deliveryTip;
    private String status;
    private String foodCategory;
    private List<ProductResponse> products;
    private String username;


    public static RestaurantResponse fromEntity(Restaurant restaurant) {
        List<ProductResponse> productResponses = restaurant.getProducts().stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .status(product.getStatus())
                        .restaurantName(product.getRestaurant().getName())
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
                restaurant.getFoodCategory().getName(),
                productResponses,
                restaurant.getUser().getName()
        );
    }



}


