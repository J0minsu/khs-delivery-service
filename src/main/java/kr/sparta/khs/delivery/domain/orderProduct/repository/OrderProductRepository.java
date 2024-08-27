package kr.sparta.khs.delivery.domain.orderProduct.repository;

import kr.sparta.khs.delivery.domain.orderProduct.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, UUID> {
    List<OrderProduct> findByOrderId(UUID orderId);
}
