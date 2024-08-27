package kr.sparta.khs.delivery.domain.report.repository;

import kr.sparta.khs.delivery.domain.report.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {
    Page<Report> findByUserId(Integer userId, Pageable pageable);
}
