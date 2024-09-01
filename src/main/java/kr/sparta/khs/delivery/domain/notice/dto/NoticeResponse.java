package kr.sparta.khs.delivery.domain.notice.dto;

import jakarta.persistence.Column;
import kr.sparta.khs.delivery.domain.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResponse {

    private UUID id;

    private String title;

    private String contents;

    private String writer;


    public static NoticeResponse fromEntity(Notice notice) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContents(),
                notice.getWriter()
        );

    }


}
