package kr.sparta.khs.delivery.domain.product.repository;

import kr.sparta.khs.delivery.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
