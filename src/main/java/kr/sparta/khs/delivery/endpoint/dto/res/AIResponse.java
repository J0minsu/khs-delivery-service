package kr.sparta.khs.delivery.endpoint.dto.res;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIResponse {

    private UUID id;
    private String prompt;
    private String answer;
    private LocalDateTime createdAt;

}
