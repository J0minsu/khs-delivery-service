package kr.sparta.khs.delivery.endpoint.dto.req;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportSolveRequest {

    private Integer handlerId;
    private String answer;

}
