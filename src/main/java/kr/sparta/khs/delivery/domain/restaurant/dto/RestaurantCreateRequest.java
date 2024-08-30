package kr.sparta.khs.delivery.domain.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantCreateRequest {


    private String name;

    private String address;

    private String phone;

    private int minPrice;

    private String operationHours;

    private String closedDays;

    private String deliveryTip;

    private String status;

    private String foodCategoryName;


}
