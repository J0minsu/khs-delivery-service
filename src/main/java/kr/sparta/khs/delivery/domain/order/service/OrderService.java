package kr.sparta.khs.delivery.domain.order.service;

import kr.sparta.khs.delivery.domain.order.dto.OrderResponse;
import kr.sparta.khs.delivery.domain.order.entity.DeliveryStatus;
import kr.sparta.khs.delivery.domain.order.entity.Order;
import kr.sparta.khs.delivery.domain.order.entity.OrderStatus;
import kr.sparta.khs.delivery.domain.order.repository.OrderRepository;
import kr.sparta.khs.delivery.domain.orderProduct.entity.OrderProduct;
import kr.sparta.khs.delivery.domain.restaurant.entity.Restaurant;
import kr.sparta.khs.delivery.domain.restaurant.repository.RestaurantRepository;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import kr.sparta.khs.delivery.endpoint.dto.req.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    public void createOrder(CreateOrderRequest req, User user) {
        User user1 = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Restaurant restaurant1 = restaurantRepository.findById(req.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        List<OrderProduct> orderProducts = req.getOrderProducts();
        int payAmount =0;
        for(OrderProduct orderProduct : orderProducts) {
            payAmount += orderProduct.getPrice()*orderProduct.getQuantity();
        }
        Order order = new Order(user1, restaurant1, req.getOrderType(), OrderStatus.REQUESTED, payAmount, DeliveryStatus.NOT_DISPATCHED, req.getRequirement(), req.getDeliveryAmount());
        orderRepository.save(order);
    }

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

    public List<OrderResponse> getOrdersByUser(User user) {
        List<Order> orders = orderRepository.findByUser(user);
        return orders.stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getOrdersByRestaurant(Restaurant restaurant) {
        List<Order> orders = orderRepository.findByRestaurant(restaurant);
        return orders.stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }
    public void acceptOrder(UUID orderId, User user) {
        OrderResponse orderResponse = getOrder(orderId, user);
        Order order = orderRepository.findById(orderResponse.getOrderId())
                        .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.updateOrderStatus(OrderStatus.ACCEPT);
    }

    public void cancelOrder(UUID orderId, User user) {
        OrderResponse orderResponse = getOrder(orderId, user);
        Order order = orderRepository.findById(orderResponse.getOrderId())
                        .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.updateOrderStatus(OrderStatus.CANCELED);
    }
}
