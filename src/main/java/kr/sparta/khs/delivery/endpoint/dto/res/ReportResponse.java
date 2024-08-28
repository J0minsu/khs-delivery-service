package kr.sparta.khs.delivery.endpoint.dto.res;

import kr.sparta.khs.delivery.domain.report.entity.ReportProcessStatus;
import kr.sparta.khs.delivery.domain.report.entity.ReportType;
import kr.sparta.khs.delivery.domain.user.vo.UserVO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportResponse {

    private UUID id;
    private ReportType reportType;
    private UUID referenceId;
    private ReportProcessStatus reportProcessStatus;
    private String reason;
    private String answer;
    private String name;
    private String handlerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}