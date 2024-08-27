package kr.sparta.khs.delivery.endpoint.dto.req;

import kr.sparta.khs.delivery.domain.report.entity.ReportType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportCreateRequest {

    private Integer userId;
    private ReportType reportType;
    private UUID referenceId;
    private String reason;

}
