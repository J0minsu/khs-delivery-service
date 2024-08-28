package kr.sparta.khs.delivery.endpoint;

import kr.sparta.khs.delivery.domain.order.entity.Order;
import kr.sparta.khs.delivery.domain.order.service.OrderService;
import kr.sparta.khs.delivery.domain.restaurant.entity.Restaurant;
import kr.sparta.khs.delivery.domain.restaurant.repository.RestaurantRepository;
import kr.sparta.khs.delivery.domain.user.entity.AuthType;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import kr.sparta.khs.delivery.endpoint.dto.req.CreateOrderRequest;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest req, @AuthenticationPrincipal SecurityUserDetails userDetails){
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        orderService.createOrder(req,user);
        return ResponseEntity.ok("Order Crate successfully");
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable UUID orderId, @AuthenticationPrincipal SecurityUserDetails userDetails){
        User user =userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Order order = orderService.getOrder(orderId, user);
        return ResponseEntity.ok(order);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Order> orders = orderService.getOrdersByUser(user);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<List<Order>> getOrdersByRestaurantId(@PathVariable UUID restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        List<Order> orders = orderService.getOrdersByRestaurant(restaurant);
        return ResponseEntity.ok(orders);
    }
    @PutMapping("/{orderId}/accept")
    public ResponseEntity<String> acceptOrder(@PathVariable UUID orderId, @AuthenticationPrincipal SecurityUserDetails userDetails){
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if(user.getAuthType().equals((AuthType.MASTER))){
            orderService.acceptOrder(orderId, user);
            return ResponseEntity.ok("Order accepted successfully");
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to accept this order");
        }
    }
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable UUID orderId, @AuthenticationPrincipal SecurityUserDetails userDetails){
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if(user.getAuthType().equals((AuthType.MASTER))){
            orderService.cancelOrder(orderId, user);
            return ResponseEntity.ok("Order cancel successfully");
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to accept this order");
        }
    }
}
