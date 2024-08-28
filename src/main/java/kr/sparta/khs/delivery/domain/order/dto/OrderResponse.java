package kr.sparta.khs.delivery.domain.order.dto;

import kr.sparta.khs.delivery.domain.order.entity.Order;
import kr.sparta.khs.delivery.domain.order.entity.OrderStatus;
import kr.sparta.khs.delivery.domain.orderProduct.dto.OrderProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private UUID orderId;
    private Integer payAmount;
    private OrderStatus orderStatus;
    private String restaurantName;
    private List<OrderProductResponse> orderProducts;

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getPayAmount(),
                order.getOrderStatus(),
                order.getRestaurant().getName(),
                order.getOrderProducts().stream()
                        .map(OrderProductResponse::from)
                        .collect(Collectors.toList())
        );
    }

}
