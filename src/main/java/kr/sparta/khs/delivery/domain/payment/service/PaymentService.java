package kr.sparta.khs.delivery.domain.payment.service;

import kr.sparta.khs.delivery.domain.order.entity.Order;
import kr.sparta.khs.delivery.domain.order.entity.PaymentStatus;
import kr.sparta.khs.delivery.domain.order.repository.OrderRepository;
import kr.sparta.khs.delivery.domain.payment.dto.PaymentRequest;
import kr.sparta.khs.delivery.domain.payment.dto.PaymentResponse;
import kr.sparta.khs.delivery.domain.payment.entity.Payment;
import kr.sparta.khs.delivery.domain.payment.repository.PaymentRepository;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public PaymentResponse getPaymentByUserIdAndPaymentId(Integer userId, UUID paymentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 유저 ID입니다."));
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않는 payment Id입니다."));
        if(payment.getOrder().getUser().equals(user)) {
            return PaymentResponse.from(payment);
        }
        else{
            throw new IllegalArgumentException("Not Authorize");
        }

    }

    public void cancelPayment(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않는 payment ID입니다"));
        payment.updatePaymentStatus(PaymentStatus.CANCELLED);
    }
    @Transactional
    public PaymentResponse createPayment(PaymentRequest req, User user) {
        User user1 = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Order order = orderRepository.findById(req.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        Payment payment = Payment.of(user1, order, order.getPayAmount(), req.getPaymentType());
        paymentRepository.save(payment);

        return PaymentResponse.from(payment);
    }
    @Transactional
    public void deletePayment(UUID paymentId, Integer userId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        if(payment.getOrder().getUser().getId() != userId){
            throw new IllegalArgumentException("Customers can only delete their own.");
        }
        payment.delete(userId);
    }
}
