package kr.sparta.khs.delivery.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.sparta.khs.delivery.config.holder.Result;
import kr.sparta.khs.delivery.domain.notice.dto.NoticeRequest;
import kr.sparta.khs.delivery.domain.notice.dto.NoticeResponse;
import kr.sparta.khs.delivery.domain.notice.service.NoticeService;
import kr.sparta.khs.delivery.endpoint.dto.req.SortStandard;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@SecurityScheme( name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
@Tag(name = "공지사항 API", description = "공지사항 관리 목적의 API Docs")
@RequestMapping("/api/v1/notice")
public class NoticeController {

    private final NoticeService noticeService;
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }


    @PostMapping
    @Secured({"MASTER"})
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<?> createNotice(@RequestBody NoticeRequest request , @AuthenticationPrincipal SecurityUserDetails userDetails) {

        noticeService.createNotice(request,userDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(Result.success("게시물 생성 완료"));

    }

    @GetMapping("/{noticeId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<?> getNoticeDetails(@PathVariable UUID noticeId) {

        return ResponseEntity.status(HttpStatus.OK).body(Result.success(noticeService.getNoticeDetails(noticeId)));

    }

    @GetMapping
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<?> getNotices(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "CREATED_DESC")
            SortStandard sort
    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, sort.getSort());

        Page<NoticeResponse> notices = noticeService.getNotices(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(Result.success(notices));
    }

    @PutMapping("/{noticeId}")
    @Secured({"MASTER"})
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<?> updateNotice(@PathVariable UUID noticeId, @RequestBody NoticeRequest request , @AuthenticationPrincipal SecurityUserDetails userDetails) {

        noticeService.updateNotice(noticeId,request,userDetails);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Result.success("공지사항 수정 완료"));

    }

    @DeleteMapping("/{noticeId}")
    @Secured({"MASTER"})
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<?> deleteNotice(@PathVariable UUID noticeId, @AuthenticationPrincipal SecurityUserDetails userDetails) {

        noticeService.deleteNotice(noticeId,userDetails);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Result.success("공지사항 삭제 완료"));

    }


}
