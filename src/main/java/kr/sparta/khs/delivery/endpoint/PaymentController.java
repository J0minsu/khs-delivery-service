package kr.sparta.khs.delivery.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.sparta.khs.delivery.config.holder.Result;
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

@RequestMapping("/api/v1/payments")
@SecurityRequirement(name = "Bearer Authentication")
@SecurityScheme( name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
@Tag(name = "결제 API", description = "결제 관리 목적의 API Docs")
@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    @PostMapping
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<PaymentResponse>> createPayment(@RequestBody PaymentRequest req, @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
        PaymentResponse paymentResponse = paymentService.createPayment(req, user);
        return ResponseEntity.ok(Result.success(paymentResponse));
    }

    @GetMapping("/user/{userId}/{paymentId}")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<PaymentResponse>> getPaymentByUserIdAndPaymentId(@PathVariable("userId") Integer userId, @PathVariable("paymentId")UUID paymentId){
        return ResponseEntity.ok(Result.success(paymentService.getPaymentByUserIdAndPaymentId(userId, paymentId)));
    }

    @PutMapping("/{paymentId}/cancel")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<String>> cancelPayment(@PathVariable("paymentId") UUID paymentId){
        paymentService.cancelPayment(paymentId);
        return ResponseEntity.ok(Result.success("cancel success"));
    }

    @DeleteMapping("/{paymentId}")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<String>> deletePayment(@PathVariable("paymentId") UUID paymentId, @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetails userDetails) {
        paymentService.deletePayment(paymentId, userDetails.getId());
        return ResponseEntity.ok(Result.success("delete success"));
    }
}
