package kr.sparta.khs.delivery.domain.product.dto;

import kr.sparta.khs.delivery.domain.product.entity.Product;
import kr.sparta.khs.delivery.domain.restaurant.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private UUID id;

    private String name;

    private String description;

    private int price;

    private String status;

    private String restaurantName;




    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStatus(),
                product.getRestaurant().getName()
        );
    }


}
