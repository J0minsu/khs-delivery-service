package kr.sparta.khs.delivery.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.sparta.khs.delivery.config.holder.Result;
import kr.sparta.khs.delivery.domain.ai.service.AIService;
import kr.sparta.khs.delivery.domain.ai.vo.AIVO;
import kr.sparta.khs.delivery.domain.report.vo.ReportVO;
import kr.sparta.khs.delivery.domain.review.service.ReviewService;
import kr.sparta.khs.delivery.domain.user.service.UserService;
import kr.sparta.khs.delivery.endpoint.dto.req.AIRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.SortStandard;
import kr.sparta.khs.delivery.endpoint.dto.res.AIResponse;
import kr.sparta.khs.delivery.endpoint.dto.res.ReportResponse;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import kr.sparta.khs.delivery.util.CommonApiResponses;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@SecurityScheme( name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
@Tag(name = "AI 요청 API", description = "AI 요청 내역 관리 목적의 API Docs")
@CommonApiResponses
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/ais")
public class AIController {

    private final AIService aiService;
    private final UserService userService;


    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    @Operation(summary = "AI 사용자 별 내역 조회", description = "AI 사용자 별 내역 조회")
    public ResponseEntity<Result<Page<AIResponse>>> findByUser(
            @PathVariable Integer userId,
            @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {

        Page<AIVO> aiResultList = aiService.findByUser(userId);

        Page<AIResponse> result = aiResultList.map(this::toAIResponse);

        return ResponseEntity.ok(Result.success(result));

    }

    @GetMapping("/{aiId}")
    @Operation(summary = "AI 아이디로 조회", description = "AI 아이디로 조회")
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    public ResponseEntity<Result<AIResponse>> findById(
            @PathVariable("aiId") UUID aiId,
            @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetails userDetails) {

        AIVO ai = aiService.findById(aiId);

        AIResponse result = toAIResponse(ai);

        return ResponseEntity.ok(Result.success(result));

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    @Operation(summary = "AI 내역 조회(search)", description = "AI 내역 조회(search)")
    public ResponseEntity<Result<Page<AIResponse>>> search(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "CREATED_DESC") SortStandard sort) {

        size = switch (size) {
            case 30 -> 30;
            case 50 -> 50;
            default -> 10;
        };

        Page<AIVO> reports =  aiService.findReports(
                keyword, PageRequest.of(pageNumber, size, sort.getSort())
        );

        Page<AIResponse> result = reports.map(this::toAIResponse);

        return ResponseEntity.ok(Result.success(result));

    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<AIResponse>> requestAI(
            @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetails userDetails,
                                    @RequestBody AIRequest request) {

        request.setUserId(userDetails.getId());

        AIVO ai = aiService.create(request);

        AIResponse result = toAIResponse(ai);

        return ResponseEntity.ok(Result.success(result));
    }

    @DeleteMapping("/{aiId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    @Operation(summary = "AI 내역 비활성화", description = "AI 내역 비활성화")
    public ResponseEntity<Void> deleteAI(
            @PathVariable UUID aiId,
            @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetails userDetails) {

        aiService.delete(aiId, userDetails.getId());

        return ResponseEntity.noContent().build();

    }

    public AIResponse toAIResponse(AIVO aiVO) {
        return new AIResponse(aiVO.getId(), aiVO.getPrompt(), aiVO.getAnswer(), aiVO.getCreatedAt());
    }
}
