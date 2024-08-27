package kr.sparta.khs.delivery.domain.report.service;

import kr.sparta.khs.delivery.domain.report.entity.Report;
import kr.sparta.khs.delivery.domain.report.entity.ReportProcessStatus;
import kr.sparta.khs.delivery.domain.report.repository.ReportRepository;
import kr.sparta.khs.delivery.domain.report.vo.ReportVO;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import kr.sparta.khs.delivery.domain.user.service.UserService;
import kr.sparta.khs.delivery.endpoint.dto.req.ReportCreateRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.ReportSolveRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReportVO createReport(ReportCreateRequest request) {

        User user = userRepository.getOne(request.getUserId());

        Report report = Report.create(
                request.getReportType(), request.getReferenceId(),
                ReportProcessStatus.REQUEST, request.getReason(),
                null, user, null);

        Report save = reportRepository.save(report);

        return save.toVO();

    }

    public ReportVO getReport(UUID reportId) {

        Report report = findById(reportId);

        return report.toVO();

    }

    public Page<ReportVO> getReportsByUser(Integer userId, Pageable pageable) {

        Page<Report> reports = reportRepository.findByUserId(userId, pageable);

        Page<ReportVO> result = reports.map(Report::toVO);

        return result;

    }

    @Transactional
    public ReportVO accept(UUID reportId, Integer reportHandlerId) {

        Report report = findById(reportId);

        User reportHandler = userRepository.getOne(reportHandlerId);

        report.accept(reportHandler);

        return report.toVO();
    }


    @Transactional
    public ReportVO solve(UUID reportId, ReportSolveRequest request) {

        Report report = findById(reportId);

        User reportHandler = userRepository.getOne(request.getHandlerId());

        report.solve(report.getAnswer(), reportHandler);

        return report.toVO();

    }



    private Report findById(UUID reportId) {
        return reportRepository.findById(reportId).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public ReportVO delete(UUID reportId, Integer reportHandlerId) {

        Report report = findById(reportId);

        report.delete(reportHandlerId);

        return report.toVO();

    }

}
