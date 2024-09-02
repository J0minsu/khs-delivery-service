package kr.sparta.khs.delivery.domain.payment.dto;

import kr.sparta.khs.delivery.domain.order.entity.OrderStatus;
import kr.sparta.khs.delivery.domain.order.entity.PaymentStatus;
import kr.sparta.khs.delivery.domain.payment.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private UUID paymentId;
    private UUID orderId;
    private UUID restaurantId;
    private Integer paymentAmount;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;

    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getOrder().getRestaurant().getId(),
                payment.getOrder().getPayAmount(),
                payment.getOrder().getOrderStatus(),
                payment.getPaymentStatus()
        );
    }

//    {
//        "paymentId": "FDS3FA-ASSDAF-324SGS-A3D4BT-ZXCB31-1168CB",
//            "orderId": "FDS3FA-ASSDAF-324SGS-A3D4BT-ZXCB31-1168CB",
//            "restaurantId": "FDS3FA-ASSDAF-324SGS-A3D4BT-ZXCB31-1168CB",
//            "paymentAmount": 17000,
//            "orderStatus": "FINISH",
//            "paymentAt": "2024-08-23 16:13:22",
//            "paymentStatus": "DONE"
//    }
}
