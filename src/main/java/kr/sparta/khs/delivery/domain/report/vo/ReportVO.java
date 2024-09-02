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

    private final UUID id;
    private final ReportType reportType;
    private final UUID referenceId;
    private final ReportProcessStatus reportProcessStatus;
    private final String reason;
    private final String answer;
    private final UserVO user;
    private final UserVO reportHandler;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
    private final Integer createdBy;
    private final Integer updatedBy;
    private final Integer deletedBy;
    private final boolean isDeleted;
    
}
