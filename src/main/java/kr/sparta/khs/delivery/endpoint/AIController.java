package kr.sparta.khs.delivery.endpoint;

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
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ais")
public class AIController {

    private final AIService aiService;
    private final UserService userService;


    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    public ResponseEntity findByUser(
            @PathVariable Integer userId,
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {

        Page<AIVO> aiResultList = aiService.findByUser(userId);

        Page<AIResponse> result = aiResultList.map(this::toAIResponse);

        return ResponseEntity.ok(result);

    }

    @GetMapping("/{aiId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    public ResponseEntity findById(
            @PathVariable("aiId") UUID aiId,
            @AuthenticationPrincipal SecurityUserDetails userDetails) {

        AIVO ai = aiService.findById(aiId);

        AIResponse result = toAIResponse(ai);

        return ResponseEntity.ok(result);

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    public ResponseEntity search(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "CREATED_DESC") SortStandard sort) {

        Page<AIVO> reports =  aiService.findReports(
                keyword, PageRequest.of(pageNumber, size, sort.getSort())
        );

        Page<AIResponse> result = reports.map(this::toAIResponse);

        return ResponseEntity.ok(result);

    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    public ResponseEntity requestAI(@AuthenticationPrincipal SecurityUserDetails userDetails,
                                    @RequestBody AIRequest request) {

        request.setUserId(userDetails.getId());

        AIVO ai = aiService.create(request);

        AIResponse result = toAIResponse(ai);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{aiId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    public ResponseEntity deleteAI(
            @PathVariable UUID aiId,
            @AuthenticationPrincipal SecurityUserDetails userDetails) {

        aiService.delete(aiId, userDetails.getId());

        return ResponseEntity.noContent().build();

    }

    public AIResponse toAIResponse(AIVO aiVO) {
        return new AIResponse(aiVO.getId(), aiVO.getPrompt(), aiVO.getAnswer(), aiVO.getCreatedAt());
    }
}
