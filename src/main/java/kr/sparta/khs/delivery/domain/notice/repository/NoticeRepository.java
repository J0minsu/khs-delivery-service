package kr.sparta.khs.delivery.domain.notice.repository;

import kr.sparta.khs.delivery.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NoticeRepository extends JpaRepository<Notice, UUID> {
}
