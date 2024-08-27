package kr.sparta.khs.delivery.domain.ai.repository;

import kr.sparta.khs.delivery.domain.ai.entity.AI;
import kr.sparta.khs.delivery.domain.ai.vo.AIVO;
import kr.sparta.khs.delivery.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AIRepository extends JpaRepository<AI, UUID> {
    Page<AI> findByRequestUser(User user);
}
