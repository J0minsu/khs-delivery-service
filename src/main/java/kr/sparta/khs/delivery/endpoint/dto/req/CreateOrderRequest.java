package kr.sparta.khs.delivery.endpoint.dto.req;

import kr.sparta.khs.delivery.domain.order.entity.OrderType;
import kr.sparta.khs.delivery.domain.orderProduct.entity.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private UUID restaurantId;
    private OrderType orderType;
    private String deliveryAddress;
    private String requirement;
    private Integer deliveryAmount;
    private List<OrderProduct> orderProducts;

//    {
//        "restaurantId": "DFGJID-256VC7-ASNWQ7-AA3G3J-722DHC-KJJ341",
//            "orderType": "DELIVERY",
//            "deliveryAddress": "경기도 성남시 분당구 야탑아파트 1동 2호"
//        "requirement": "현관 비밀반호 5##62",
//            "deliveryAmount" : 3500
//            "orderProducts": [
//        "productId": "DFGJID-256VC7-ASNWQ7-AA3G3J-722DHC-KJJ341",
//            "quantity": 2,
//            "price": 8500,
//	]
//    }
}
