package kr.sparta.khs.delivery.domain.report.vo;

import kr.sparta.khs.delivery.domain.report.entity.ReportProcessStatus;
import kr.sparta.khs.delivery.domain.report.entity.ReportType;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@ToString
@Getter
public class ReportVO {

    private UUID id;
    private ReportType reportType;
    private UUID referenceId;
    private ReportProcessStatus reportProcessStatus;
    private String reason;
    private String answer;
    private UserVO user;
    private UserVO reportHandler;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
    private final Integer createdBy;
    private final Integer updatedBy;
    private final Integer deletedBy;
    
}
