package kr.sparta.khs.delivery.domain.ai.repository;

import kr.sparta.khs.delivery.domain.ai.entity.AI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AIRepository extends JpaRepository<AI, UUID> {
    Page<AI> findByRequestUserId(Integer userId, Pageable pageable);

    /*@Query("""
        SELECT ai
          FROM kr.sparta.khs.delivery.domain.ai.entity.AI ai
         WHERE ai.prompt = :keyword
    """)*/
    Page<AI> findByPromptStartingWith(String keyword, PageRequest pageRequest);
}
