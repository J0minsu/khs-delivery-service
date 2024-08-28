package kr.sparta.khs.delivery.endpoint;

import kr.sparta.khs.delivery.domain.orderProduct.dto.OrderProductResponse;
import kr.sparta.khs.delivery.domain.orderProduct.entity.OrderProduct;
import kr.sparta.khs.delivery.domain.orderProduct.service.OrderProductService;
import kr.sparta.khs.delivery.endpoint.dto.req.OrderProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orderproducts")
@RequiredArgsConstructor
public class OrderProductController {
    private final OrderProductService orderProductService;

//    @PostMapping
//    public ResponseEntity<OrderProduct> createOrderProduct(@RequestParam UUID orderId, @RequestParam UUID productId, @RequestParam int quantity) {
//        OrderProduct orderProduct = orderProductService.createOrderProduct(orderId, productId, quantity);
//        return ResponseEntity.ok(orderProduct);
//    }

    @PostMapping
    public ResponseEntity<String> createOrderProduct(@RequestBody OrderProductDto orderProductDto) {
        orderProductService.createOrderProduct(orderProductDto);
        return ResponseEntity.ok("create order product!");
    }
    @GetMapping("/{orderProductId}")
    public ResponseEntity<OrderProductResponse> getOrderProductById(@PathVariable UUID orderProductId) {
        OrderProductResponse orderProduct = orderProductService.getOrderProductById(orderProductId);
        return ResponseEntity.ok(orderProduct);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderProductResponse>> getOrderProductsByOrderId(@PathVariable UUID orderId) {
        List<OrderProductResponse> orderProducts = orderProductService.getOrderProductsByOrderId(orderId);
        return ResponseEntity.ok(orderProducts);
    }

    @PutMapping("/{orderProductId}")
    public ResponseEntity<String> updateOrderProduct(@PathVariable UUID orderProductId, @RequestParam int quantity) {
        orderProductService.updateOrderProduct(orderProductId, quantity);
        return ResponseEntity.ok("updatedOrderProduct");
    }

    @DeleteMapping("/{orderProductId}")
    public ResponseEntity<Void> deleteOrderProduct(@PathVariable UUID orderProductId) {
        orderProductService.deleteOrderProduct(orderProductId);
        return ResponseEntity.noContent().build();
    }
}
