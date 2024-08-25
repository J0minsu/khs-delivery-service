package kr.sparta.khs.delivery.domain.ai.repository;

import kr.sparta.khs.delivery.domain.ai.entity.AI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AIRepository extends JpaRepository<AI, UUID> {
}
