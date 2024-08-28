package kr.sparta.khs.delivery.domain.report.repository;

import kr.sparta.khs.delivery.domain.report.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {
    Page<Report> findByUserId(Integer userId, Pageable pageable);

    @Query("""
        SELECT report
          FROM kr.sparta.khs.delivery.domain.report.entity.Report report
         WHERE report.reason = :keyword
    """)
    Page<Report> findByKeyword(String keyword, PageRequest pageRequest);
}
