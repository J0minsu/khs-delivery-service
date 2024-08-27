package kr.sparta.khs.delivery.domain.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantCreateRequest {


    private UUID id;

    private String name;

    private String address;

    private String phone;

    private int minPrice;

    private String operationHours;

    private String closedDays;

    private String deliveryTip;

    private String status;

    private UUID FoodCategoryId;


}
