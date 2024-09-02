package kr.sparta.khs.delivery.domain.orderProduct.dto;

import kr.sparta.khs.delivery.domain.orderProduct.entity.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductResponse {
    private String productName;
    private Integer quantity;
    private Integer price;
    private Integer totalPrice;

    public static OrderProductResponse from(OrderProduct orderProduct) {
        return new OrderProductResponse(
                orderProduct.getName(),
                orderProduct.getQuantity(),
                orderProduct.getPrice(),
                orderProduct.getPrice()*orderProduct.getQuantity()
        );
    }
}
