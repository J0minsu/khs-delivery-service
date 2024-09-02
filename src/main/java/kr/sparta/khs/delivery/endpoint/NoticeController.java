package kr.sparta.khs.delivery.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.sparta.khs.delivery.config.holder.Result;
import kr.sparta.khs.delivery.domain.notice.dto.NoticeRequest;
import kr.sparta.khs.delivery.domain.notice.dto.NoticeResponse;
import kr.sparta.khs.delivery.domain.notice.service.NoticeService;
import kr.sparta.khs.delivery.endpoint.dto.req.SortStandard;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import kr.sparta.khs.delivery.util.CommonApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@SecurityScheme( name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
@Tag(name = "공지사항 API", description = "공지사항 관리 목적의 API Docs")
@CommonApiResponses
@RequestMapping("/api/v1/notice")
public class NoticeController {

    private final NoticeService noticeService;
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }


    @PostMapping
    @Secured({"MASTER"})
    @Operation(summary = "공지사항 생성", description = "새로운 공지사항을 생성")
    public ResponseEntity<?> createNotice(@RequestBody NoticeRequest request , @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetails userDetails) {

        noticeService.createNotice(request,userDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(Result.success("게시물 생성 완료"));

    }

    @GetMapping("/{noticeId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "공지사항 조회", description = "특정 공지사항의 세부 내용을 조회")
    @ApiResponse(responseCode = "200", description = "공지사항 조회 완료")
    public ResponseEntity<?> getNoticeDetails(@PathVariable UUID noticeId) {

        return ResponseEntity.status(HttpStatus.OK).body(Result.success(noticeService.getNoticeDetails(noticeId)));

    }

    @GetMapping
    @Operation(summary = "공지사항 목록 조회", description = "공지사항 목록을 페이지네이션하여 조회")
    @ApiResponse(responseCode = "200", description = "공지사항 목록 조회 완료")
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
    @Operation(summary = "공지사항 수정", description = "특정 공지사항을 수정")
    public ResponseEntity<?> updateNotice(@PathVariable UUID noticeId, @RequestBody NoticeRequest request , @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetails userDetails) {

        noticeService.updateNotice(noticeId,request,userDetails);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Result.success("공지사항 수정 완료"));

    }

    @DeleteMapping("/{noticeId}")
    @Secured({"MASTER"})
    @Operation(summary = "공지사항 삭제", description = "공지사항은 숨김처리 x 삭제로 구현했습니다. ")
    public ResponseEntity<?> deleteNotice(@PathVariable UUID noticeId, @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetails userDetails) {

        noticeService.deleteNotice(noticeId,userDetails);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Result.success("공지사항 삭제 완료"));

    }


}
