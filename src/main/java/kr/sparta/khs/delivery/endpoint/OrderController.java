package kr.sparta.khs.delivery.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.sparta.khs.delivery.config.holder.Result;
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
@SecurityRequirement(name = "Bearer Authentication")
@SecurityScheme( name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
@Tag(name = "주문 API", description = "주문 관리 목적의 API Docs")
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    @PreAuthorize("hasAnyRole('CUSTOMER, MANAGER')")
    @PostMapping
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<String>> createOrder(@RequestBody CreateOrderRequest req, @AuthenticationPrincipal SecurityUserDetails userDetails){
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        orderService.createOrder(req,user);
        return ResponseEntity.ok(Result.success("Order Crate successfully"));
    }
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MASTER')")
    @GetMapping("/{orderId}")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<OrderResponse>> getOrder(@PathVariable UUID orderId, @AuthenticationPrincipal SecurityUserDetails userDetails){
        User user =userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        OrderResponse order = orderService.getOrder(orderId, user);
        return ResponseEntity.ok(Result.success(order));
    }
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MASTER')")
    @GetMapping("/user/{userId}")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<List<OrderResponse>>> getOrdersByUserId(@PathVariable Integer userId, @AuthenticationPrincipal SecurityUserDetails userDetails) {
        if (!userDetails.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<OrderResponse> orders = orderService.getOrdersByUser(user);
        return ResponseEntity.ok(Result.success(orders));
    }
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    @GetMapping("/restaurants/{restaurantId}")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<List<OrderResponse>>> getOrdersByRestaurantId(@PathVariable UUID restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        List<OrderResponse> orders = orderService.getOrdersByRestaurant(restaurant);
        return ResponseEntity.ok(Result.success(orders));
    }
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{orderId}/accept")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<String>> acceptOrder(@PathVariable UUID orderId, @AuthenticationPrincipal SecurityUserDetails userDetails){
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if(user.getAuthType().equals((AuthType.MASTER))){
            orderService.acceptOrder(orderId, user);
            return ResponseEntity.ok(Result.success("Order accepted successfully"));
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to accept this order");
        }
    }
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{orderId}/cancel")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<String>> cancelOrder(@PathVariable UUID orderId, @AuthenticationPrincipal SecurityUserDetails userDetails){
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if(user.getAuthType().equals((AuthType.MASTER))){
            orderService.cancelOrder(orderId, user);
            return ResponseEntity.ok(Result.success("Order cancel successfully"));
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to accept this order");
        }
    }
    @GetMapping("/search")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<Page<OrderResponse>>> searchOrders(
            @RequestBody OrderSearch search,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "CREATED_DESC") SortStandard sort) {
        Page<OrderResponse> orders = orderService.searchOrders(search, page, size, sort);
        return ResponseEntity.ok(Result.success(orders));
    }
    @PreAuthorize("hasAnyRole('MANAGER','MASTER')")
    @DeleteMapping("/{orderId}")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<String>> deleteOrder(@PathVariable UUID orderId, @AuthenticationPrincipal SecurityUserDetails userDetails) {
        orderService.deleteOrder(orderId, userDetails.getId());
        return ResponseEntity.ok(Result.success("Order deleted successfully"));
    }

}
