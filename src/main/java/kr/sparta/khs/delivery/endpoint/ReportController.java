package kr.sparta.khs.delivery.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.sparta.khs.delivery.config.holder.Result;
import kr.sparta.khs.delivery.domain.report.service.ReportService;
import kr.sparta.khs.delivery.domain.report.vo.ReportVO;
import kr.sparta.khs.delivery.domain.user.entity.AuthType;
import kr.sparta.khs.delivery.domain.user.service.UserService;
import kr.sparta.khs.delivery.endpoint.dto.req.ReportCreateRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.ReportSolveRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.SortStandard;
import kr.sparta.khs.delivery.endpoint.dto.res.ReportResponse;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@SecurityScheme( name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
@Tag(name = "신고 내역 API", description = "신고 내역 관리 목적의 API Docs")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<ReportResponse>> create(
            @RequestBody ReportCreateRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetails userDetails) {


        request.setUserId(userDetails.getId());

        ReportVO report = reportService.createReport(request);

        ReportResponse result = toResponse(report);

        return ResponseEntity.ok(Result.success(result));

    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<Page<ReportResponse>>> findMyReports(
            @PathVariable Integer userId,
            Pageable pageable) {

        Page<ReportVO> reports = reportService.getReportsByUser(userId, pageable);

        Page<ReportResponse> result = reports.map(this::toResponse);

        return ResponseEntity.ok(Result.success(result));

    }

    @GetMapping("/{reportId}")
    @PreAuthorize("hasAnyRole('MASTER')")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<ReportResponse>> findReport(
            @PathVariable UUID reportId,
            @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetails userDetails) {

        ReportVO report = reportService.getReport(reportId);

        if(userDetails.getAuthType() == AuthType.CUSTOMER
                && report.getUser().getId() != userDetails.getId()) {
            throw new IllegalArgumentException("report search is available only own(customer)");
        }

        ReportResponse result = toResponse(report);

        return ResponseEntity.ok(Result.success(result));

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MASTER')")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<Page<ReportResponse>>> search(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "CREATED_DESC") SortStandard sort) {


        size = switch (size) {
            case 30 -> 30;
            case 50 -> 50;
            default -> 10;
        };

        Page<ReportVO> reports =  reportService.search(
                keyword, PageRequest.of(pageNumber, size, sort.getSort())
        );

        Page<ReportResponse> result = reports.map(this::toResponse);

        return ResponseEntity.ok(Result.success(result));

    }

    @PatchMapping("/{reportId}/accept")
    @PreAuthorize("hasAnyRole('MASTER')")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<ReportResponse>> acceptReport(
            @PathVariable UUID reportId,
            @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetails userDetails) {

        ReportVO report = reportService.accept(reportId, userDetails.getId());

        ReportResponse result = toResponse(report);

        return ResponseEntity.ok(Result.success(result));

    }

    @PatchMapping("/{reportId}/solve")
    @PreAuthorize("hasAnyRole('MASTER')")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<ReportResponse>> solveReport(
            @PathVariable UUID reportId,
            @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetails userDetails,
            @RequestBody ReportSolveRequest request) {

        ReportVO report = reportService.solve(reportId, request);

        ReportResponse result = toResponse(report);

        return ResponseEntity.ok(Result.success(result));

    }

    @DeleteMapping("/{reportId}")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<Void>> delete(
            @PathVariable UUID reportId,
            @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetails userDetails) {

        reportService.delete(reportId, userDetails.getId());

        return ResponseEntity.noContent().build();

    }

    private ReportResponse toResponse(ReportVO report) {

        return ReportResponse.builder()
                .id(report.getId())
                .reportType(report.getReportType())
                .referenceId(report.getReferenceId())
                .reportProcessStatus(report.getReportProcessStatus())
                .reason(report.getReason())
                .answer(report.getAnswer())
                .name(report.getUser().getName())
                .handlerName(report.getReportHandler().getName())
                .createdAt(report.getCreatedAt())
                .updatedAt(report.getUpdatedAt())
                .build();

    }

}
