package kr.sparta.khs.delivery.domain.restaurant.dto;

import kr.sparta.khs.delivery.domain.product.dto.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

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
    private String Username;

}