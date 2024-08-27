package kr.sparta.khs.delivery.domain.orderProduct.service;

import kr.sparta.khs.delivery.domain.order.entity.Order;
import kr.sparta.khs.delivery.domain.order.repository.OrderRepository;
import kr.sparta.khs.delivery.domain.orderProduct.entity.OrderProduct;
import kr.sparta.khs.delivery.domain.orderProduct.repository.OrderProductRepository;
import kr.sparta.khs.delivery.domain.product.entity.Product;
import kr.sparta.khs.delivery.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderProduct createOrderProduct(UUID orderId, UUID productId, int quantity) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        OrderProduct orderProduct = new OrderProduct(order, product, quantity, product.getPrice());
        return orderProductRepository.save(orderProduct);
    }

    public List<OrderProduct> getOrderProductsByOrderId(UUID orderId) {
        return orderProductRepository.findByOrderId(orderId);
    }

    public OrderProduct getOrderProductById(UUID orderProductId) {
        return orderProductRepository.findById(orderProductId)
                .orElseThrow(() -> new IllegalArgumentException("OrderProduct not found"));
    }

    @Transactional
    public OrderProduct updateOrderProduct(UUID orderProductId, int quantity) {
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId)
                .orElseThrow(() -> new IllegalArgumentException("OrderProduct not found"));


        orderProduct.updateQuantity(quantity);
        orderProduct.updatePrice(orderProduct.getPrice());

        return orderProductRepository.save(orderProduct);
    }

    @Transactional
    public void deleteOrderProduct(UUID orderProductId) {
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId)
                .orElseThrow(() -> new IllegalArgumentException("OrderProduct not found"));

        orderProductRepository.delete(orderProduct);
    }
}
