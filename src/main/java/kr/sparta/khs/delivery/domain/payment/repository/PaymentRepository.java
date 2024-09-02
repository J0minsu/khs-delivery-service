package kr.sparta.khs.delivery.domain.payment.repository;

import kr.sparta.khs.delivery.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
