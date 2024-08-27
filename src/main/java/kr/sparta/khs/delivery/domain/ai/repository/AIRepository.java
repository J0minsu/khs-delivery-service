package kr.sparta.khs.delivery.domain.ai.repository;

import kr.sparta.khs.delivery.domain.ai.entity.AI;
import kr.sparta.khs.delivery.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AIRepository extends JpaRepository<AI, UUID> {
    Page<AI> findByRequestUserId(Integer userId, Pageable pageable);
}
