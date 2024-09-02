package kr.sparta.khs.delivery.endpoint;

import kr.sparta.khs.delivery.domain.ai.vo.AIVO;
import kr.sparta.khs.delivery.domain.order.dto.OrderResponse;
import kr.sparta.khs.delivery.domain.order.dto.OrderSearch;
import kr.sparta.khs.delivery.domain.order.entity.DeliveryStatus;
import kr.sparta.khs.delivery.domain.order.entity.Order;
import kr.sparta.khs.delivery.domain.order.entity.OrderStatus;
import kr.sparta.khs.delivery.domain.order.entity.OrderType;
import kr.sparta.khs.delivery.domain.order.service.OrderService;
import kr.sparta.khs.delivery.domain.restaurant.entity.Restaurant;
import kr.sparta.khs.delivery.domain.restaurant.repository.RestaurantRepository;
import kr.sparta.khs.delivery.domain.user.entity.AuthType;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import kr.sparta.khs.delivery.endpoint.dto.req.CreateOrderRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.SortStandard;
import kr.sparta.khs.delivery.endpoint.dto.res.AIResponse;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('CUSTOMER, MANAGER')")
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest req, @AuthenticationPrincipal SecurityUserDetails userDetails){
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        orderService.createOrder(req,user);
        return ResponseEntity.ok("Order Crate successfully");
    }
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MASTER')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable UUID orderId, @AuthenticationPrincipal SecurityUserDetails userDetails){
        User user =userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        OrderResponse order = orderService.getOrder(orderId, user);
        return ResponseEntity.ok(order);
    }
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MASTER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable Integer userId, @AuthenticationPrincipal SecurityUserDetails userDetails) {
        if (!userDetails.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<OrderResponse> orders = orderService.getOrdersByUser(user);
        return ResponseEntity.ok(orders);
    }
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByRestaurantId(@PathVariable UUID restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        List<OrderResponse> orders = orderService.getOrdersByRestaurant(restaurant);
        return ResponseEntity.ok(orders);
    }
    @PreAuthorize("hasRole('MANAGER')")
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
    @PreAuthorize("hasRole('MANAGER')")
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
    @GetMapping("/search")
    public Page<OrderResponse> searchOrders(
            @RequestBody OrderSearch search,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "CREATED_DESC") SortStandard sort) {
        return orderService.searchOrders(search, page, size, sort);
    }
    @PreAuthorize("hasAnyRole('MANAGER','MASTER')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable UUID orderId, @AuthenticationPrincipal SecurityUserDetails userDetails) {
        orderService.deleteOrder(orderId, userDetails.getId());
        return ResponseEntity.ok("Order deleted successfully");
    }

}
