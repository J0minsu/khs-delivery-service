package kr.sparta.khs.delivery.domain.payment.dto;

import kr.sparta.khs.delivery.domain.order.entity.Order;
import kr.sparta.khs.delivery.domain.payment.entity.PaymentType;
import kr.sparta.khs.delivery.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private UUID orderId;
    private PaymentType paymentType;

}
