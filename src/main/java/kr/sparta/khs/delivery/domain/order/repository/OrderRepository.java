package kr.sparta.khs.delivery.domain.order.repository;

import kr.sparta.khs.delivery.domain.order.entity.DeliveryStatus;
import kr.sparta.khs.delivery.domain.order.entity.Order;
import kr.sparta.khs.delivery.domain.order.entity.OrderStatus;
import kr.sparta.khs.delivery.domain.order.entity.OrderType;
import kr.sparta.khs.delivery.domain.restaurant.entity.Restaurant;
import kr.sparta.khs.delivery.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUser(User user);

    List<Order> findByRestaurant(Restaurant restaurant);

    Page<Order> findByTypeAndDeliveryStatusAndOrderStatus(
            OrderType type,
            DeliveryStatus deliveryStatus,
            OrderStatus orderStatus,
            Pageable pageable
);
}
