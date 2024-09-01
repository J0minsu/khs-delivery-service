package kr.sparta.khs.delivery.domain.notice.service;

import kr.sparta.khs.delivery.domain.notice.dto.NoticeRequest;
import kr.sparta.khs.delivery.domain.notice.dto.NoticeResponse;
import kr.sparta.khs.delivery.domain.notice.entity.Notice;
import kr.sparta.khs.delivery.domain.notice.repository.NoticeRepository;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository, UserRepository userRepository) {
        this.noticeRepository = noticeRepository;
    }


    @Transactional
    public void createNotice(NoticeRequest request , SecurityUserDetails userDetails) {

        Notice notice = new Notice(request,userDetails);
        noticeRepository.save(notice);

    }

    @Transactional
    public void updateNotice(UUID noticeId, NoticeRequest request, SecurityUserDetails userDetails) {

        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new IllegalArgumentException(noticeId + "는(은) 찾을수 없는 게시물 id 입니다"));

        notice.update(request,userDetails);

        noticeRepository.save(notice);

    }

    @Transactional(readOnly = true)
    public NoticeResponse getNoticeDetails(UUID noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new IllegalArgumentException(noticeId + "는(은) 찾을수 없는 게시물 id 입니다"));
        return NoticeResponse.fromEntity(notice);
    }

    @Transactional(readOnly = true)
    public Page<NoticeResponse> getNotices(Pageable pageable) {

        return noticeRepository.findAll(pageable).map(NoticeResponse::fromEntity);

    }


    @Transactional
    public void deleteNotice(UUID noticeId ,SecurityUserDetails userDetails) {

        int userId = userDetails.getId();

        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new IllegalArgumentException(noticeId + "는(은) 찾을수 없는 게시물 id 입니다"));

        if (notice.isDeleted()) {
            throw new IllegalArgumentException("이미 삭제 처리된 게시물 입니다.");
        } else {

            notice.delete(userId);
            noticeRepository.save(notice);
        }


    }
}
