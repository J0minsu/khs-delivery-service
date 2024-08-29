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
    private List<OrderProductDto> orderProducts;
    //유저에게 주거나 받을때 orderproduct에 대한 엔티티에 대한 정보를
    //orderproduct 직접생성 repository에 직접저장
    //order안에 orderProduct이라는 list형식으로 갖고있을거다. cascade를 걸면 order에서 orderproduct를 관리 할 수 있는 권한이 생긴다.


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
