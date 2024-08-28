package kr.sparta.khs.delivery.domain.orderProduct.service;

import kr.sparta.khs.delivery.domain.order.entity.Order;
import kr.sparta.khs.delivery.domain.order.repository.OrderRepository;
import kr.sparta.khs.delivery.domain.orderProduct.dto.OrderProductResponse;
import kr.sparta.khs.delivery.domain.orderProduct.entity.OrderProduct;
import kr.sparta.khs.delivery.domain.orderProduct.repository.OrderProductRepository;
import kr.sparta.khs.delivery.domain.product.entity.Product;
import kr.sparta.khs.delivery.domain.product.repository.ProductRepository;
import kr.sparta.khs.delivery.domain.product.service.ProductService;
import kr.sparta.khs.delivery.endpoint.dto.req.OrderProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public void createOrderProduct(OrderProductDto orderProductDto) {
        Order order = orderRepository.findById(orderProductDto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Product product = productRepository.findById(orderProductDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        OrderProduct orderProduct = new OrderProduct(order, product, orderProductDto.getQuantity(), product.getPrice());
        orderProductRepository.save(orderProduct);
}
    public List<OrderProductResponse> getOrderProductsByOrderId(UUID orderId) {
        List<OrderProduct> orderProducts = orderProductRepository.findByOrderId(orderId);
        return orderProducts.stream()
                .map(OrderProductResponse::from)
                .collect(Collectors.toList());
    }

    public OrderProductResponse getOrderProductById(UUID orderProductId) {
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId)
                .orElseThrow(() -> new IllegalArgumentException("OrderProduct not found"));
        return OrderProductResponse.from(orderProduct);
    }

    @Transactional
    public void updateOrderProduct(UUID orderProductId, int quantity) {
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId)
                .orElseThrow(() -> new IllegalArgumentException("OrderProduct not found"));


        orderProduct.updateQuantity(quantity);
        orderProduct.updatePrice(orderProduct.getPrice());

        orderProductRepository.save(orderProduct);
    }

    @Transactional
    public void deleteOrderProduct(UUID orderProductId) {
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId)
                .orElseThrow(() -> new IllegalArgumentException("OrderProduct not found"));

        orderProductRepository.delete(orderProduct);
    }


}
