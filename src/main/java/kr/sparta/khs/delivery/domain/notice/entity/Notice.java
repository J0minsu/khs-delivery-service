package kr.sparta.khs.delivery.domain.notice.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.common.entity.BaseEntity;
import kr.sparta.khs.delivery.domain.notice.dto.NoticeRequest;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import lombok.*;

import java.util.UUID;

@Entity(name = "p_notice")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "notice_id")
    private UUID id;

    @Column(name ="notice_title", nullable = false)
    private String title;

    @Column(name ="notice_contents", nullable = false)
    private String contents;

    @Column(name ="notice_writer", nullable = false)
    private String writer;


    public Notice(NoticeRequest request , SecurityUserDetails userDetails) {
        this.title = request.getTitle();
        this.contents = request.getContents();
        this.writer = userDetails.getName();
    }


    public void update(NoticeRequest request, SecurityUserDetails userDetails) {
        this.title = request.getTitle();
        this.contents = request.getContents();
        this.writer = userDetails.getName();
    }
}
