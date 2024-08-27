package kr.sparta.khs.delivery.endpoint;

import kr.sparta.khs.delivery.domain.orderProduct.entity.OrderProduct;
import kr.sparta.khs.delivery.domain.orderProduct.service.OrderProductService;
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

    @PostMapping
    public ResponseEntity<OrderProduct> createOrderProduct(@RequestParam UUID orderId, @RequestParam UUID productId, @RequestParam int quantity) {
        OrderProduct orderProduct = orderProductService.createOrderProduct(orderId, productId, quantity);
        return ResponseEntity.ok(orderProduct);
    }


    @GetMapping("/{orderProductId}")
    public ResponseEntity<OrderProduct> getOrderProductById(@PathVariable UUID orderProductId) {
        OrderProduct orderProduct = orderProductService.getOrderProductById(orderProductId);
        return ResponseEntity.ok(orderProduct);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderProduct>> getOrderProductsByOrderId(@PathVariable UUID orderId) {
        List<OrderProduct> orderProducts = orderProductService.getOrderProductsByOrderId(orderId);
        return ResponseEntity.ok(orderProducts);
    }

    @PutMapping("/{orderProductId}")
    public ResponseEntity<OrderProduct> updateOrderProduct(@PathVariable UUID orderProductId, @RequestParam int quantity) {
        OrderProduct updatedOrderProduct = orderProductService.updateOrderProduct(orderProductId, quantity);
        return ResponseEntity.ok(updatedOrderProduct);
    }

    @DeleteMapping("/{orderProductId}")
    public ResponseEntity<Void> deleteOrderProduct(@PathVariable UUID orderProductId) {
        orderProductService.deleteOrderProduct(orderProductId);
        return ResponseEntity.noContent().build();
    }
}
