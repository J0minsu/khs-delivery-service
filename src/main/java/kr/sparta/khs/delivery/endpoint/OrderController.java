//package kr.sparta.khs.delivery.endpoint;
//
//import kr.sparta.khs.delivery.domain.order.service.OrderService;
//import kr.sparta.khs.delivery.domain.user.entity.User;
//import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
//import kr.sparta.khs.delivery.endpoint.dto.req.CreateOrderRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class OrderController {
//    private final OrderService orderService;
//    private final UserRepository userRepository;
//    @PostMapping
//    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest req){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userId = authentication.getName();
//        User user = userRepository.findBy()
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//        orderService
//    }
//}
