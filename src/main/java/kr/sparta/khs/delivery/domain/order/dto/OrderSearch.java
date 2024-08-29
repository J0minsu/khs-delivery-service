package kr.sparta.khs.delivery.domain.order.dto;

import kr.sparta.khs.delivery.domain.order.entity.DeliveryStatus;
import kr.sparta.khs.delivery.domain.order.entity.OrderStatus;
import kr.sparta.khs.delivery.domain.order.entity.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSearch {
    private OrderType type;
    private DeliveryStatus deliveryStatus;
    private OrderStatus orderStatus;
}
