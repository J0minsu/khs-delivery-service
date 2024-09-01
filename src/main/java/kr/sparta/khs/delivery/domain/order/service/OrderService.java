package kr.sparta.khs.delivery.domain.order.service;

import kr.sparta.khs.delivery.domain.ai.entity.AI;
import kr.sparta.khs.delivery.domain.ai.vo.AIVO;
import kr.sparta.khs.delivery.domain.order.dto.OrderResponse;
import kr.sparta.khs.delivery.domain.order.dto.OrderSearch;
import kr.sparta.khs.delivery.domain.order.entity.DeliveryStatus;
import kr.sparta.khs.delivery.domain.order.entity.Order;
import kr.sparta.khs.delivery.domain.order.entity.OrderStatus;
import kr.sparta.khs.delivery.domain.order.entity.OrderType;
import kr.sparta.khs.delivery.domain.order.repository.OrderRepository;
import kr.sparta.khs.delivery.domain.orderProduct.entity.OrderProduct;

import kr.sparta.khs.delivery.domain.product.entity.Product;
import kr.sparta.khs.delivery.domain.product.repository.ProductRepository;
import kr.sparta.khs.delivery.domain.restaurant.entity.Restaurant;
import kr.sparta.khs.delivery.domain.restaurant.repository.RestaurantRepository;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import kr.sparta.khs.delivery.endpoint.dto.req.CreateOrderRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.OrderProductDto;
import kr.sparta.khs.delivery.endpoint.dto.req.SortStandard;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Transactional
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void createOrder(CreateOrderRequest req, User user) {
        User user1 = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Restaurant restaurant1 = restaurantRepository.findById(req.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        List<OrderProductDto> orderProducts = req.getOrderProducts();

        Order order = Order.of(user1, restaurant1, req.getOrderType(), OrderStatus.REQUESTED, DeliveryStatus.NOT_DISPATCHED, req.getRequirement(), req.getDeliveryAmount(),orderProducts);
        orderRepository.save(order);
    }
    @Transactional(readOnly = true)
    public OrderResponse getOrder(UUID orderId, User user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        OrderResponse orderResponse = OrderResponse.from(order);
        if(order.getUser().equals(user)) {
            return orderResponse;
        }
        else{
            throw new IllegalArgumentException("User not found");
        }
    }
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByUser(User user) {
        List<Order> orders = orderRepository.findByUser(user);
        return orders.stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByRestaurant(Restaurant restaurant) {
        List<Order> orders = orderRepository.findByRestaurant(restaurant);
        return orders.stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }
    @Transactional
    public void acceptOrder(UUID orderId, User user) {
        OrderResponse orderResponse = getOrder(orderId, user);
        Order order = orderRepository.findById(orderResponse.getOrderId())
                        .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.updateOrderStatus(OrderStatus.ACCEPT);
    }
    @Transactional
    public void cancelOrder(UUID orderId, User user) {
        OrderResponse orderResponse = getOrder(orderId, user);
        Order order = orderRepository.findById(orderResponse.getOrderId())
                        .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.updateOrderStatus(OrderStatus.CANCELED);
    }
    @Transactional(readOnly = true)
    public Page<OrderResponse> searchOrders(OrderSearch search, int page, int size, SortStandard sort) {
        Pageable pageable = PageRequest.of(page, size, sort.getSort());
        Page<Order> orders = orderRepository.findByTypeAndDeliveryStatusAndOrderStatus(search.getType(), search.getDeliveryStatus(), search.getOrderStatus(), pageable);
        return orders.map(OrderResponse::from);
    }
    @Transactional
    public void deleteOrder(UUID orderId, Integer userid) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if(order.getUser().getId() != userid) {
            throw new IllegalArgumentException("This order is not yours");
        }
        else{
            order.delete(userid);
        }
    }

//    public Page<OrderResponse> findOrders(String keyword, PageRequest pageRequest) {
//        Page<Order> reports = orderRepository.findByKeyword(keyword, pageRequest);
//
//        Page<OrderResponse> result = reports.map(Order::toOrderResponse);
//
//        return result;
//    }
}
