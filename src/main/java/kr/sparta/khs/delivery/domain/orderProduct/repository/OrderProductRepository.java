package kr.sparta.khs.delivery.domain.orderProduct.repository;

import kr.sparta.khs.delivery.domain.orderProduct.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
