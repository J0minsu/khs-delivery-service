package kr.sparta.khs.delivery.endpoint;

import kr.sparta.khs.delivery.domain.payment.dto.PaymentRequest;
import kr.sparta.khs.delivery.domain.payment.dto.PaymentResponse;
import kr.sparta.khs.delivery.domain.payment.service.PaymentService;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/payments")
@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest req, @AuthenticationPrincipal SecurityUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
        PaymentResponse paymentResponse = paymentService.createPayment(req, user);
        return ResponseEntity.ok(paymentResponse);
    }

    @GetMapping("/user/{userId}/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentByUserIdAndPaymentId(@PathVariable("userId") Integer userId, @PathVariable("paymentId")UUID paymentId){
        return ResponseEntity.ok(paymentService.getPaymentByUserIdAndPaymentId(userId, paymentId));
    }

    @PutMapping("/{paymentId}/cancel")
    public ResponseEntity<String> cancelPayment(@PathVariable("paymentId") UUID paymentId){
        paymentService.cancelPayment(paymentId);
        return ResponseEntity.ok("cancel success");
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<String> deletePayment(@PathVariable("paymentId") UUID paymentId, @AuthenticationPrincipal SecurityUserDetails userDetails) {
        paymentService.deletePayment(paymentId, userDetails.getId());
        return ResponseEntity.ok("delete success");
    }
}
