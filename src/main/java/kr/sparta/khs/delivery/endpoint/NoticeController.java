package kr.sparta.khs.delivery.endpoint;

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
@RequestMapping("/api/v1/notice")
public class NoticeController {

    private final NoticeService noticeService;
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }


    @PostMapping
    @Secured({"MASTER"})
    public ResponseEntity<?> createNotice(@RequestBody NoticeRequest request , @AuthenticationPrincipal SecurityUserDetails userDetails) {

        noticeService.createNotice(request,userDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(Result.success("게시물 생성 완료"));

    }

    @GetMapping("/{noticeId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getNoticeDetails(@PathVariable UUID noticeId) {

        return ResponseEntity.status(HttpStatus.OK).body(Result.success(noticeService.getNoticeDetails(noticeId)));

    }

    @GetMapping
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
    public ResponseEntity<?> updateNotice(@PathVariable UUID noticeId, @RequestBody NoticeRequest request , @AuthenticationPrincipal SecurityUserDetails userDetails) {

        noticeService.updateNotice(noticeId,request,userDetails);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Result.success("공지사항 수정 완료"));

    }

    @DeleteMapping("/{noticeId}")
    @Secured({"MASTER"})
    public ResponseEntity<?> deleteNotice(@PathVariable UUID noticeId, @AuthenticationPrincipal SecurityUserDetails userDetails) {

        noticeService.deleteNotice(noticeId,userDetails);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Result.success("공지사항 삭제 완료"));

    }


}
