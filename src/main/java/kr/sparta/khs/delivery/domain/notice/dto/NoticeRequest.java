package kr.sparta.khs.delivery.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRequest {


    private String title;

    private String contents;

    private String writer;




}
