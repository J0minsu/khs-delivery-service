package kr.sparta.khs.delivery.domain.ai.vo;

import kr.sparta.khs.delivery.domain.user.vo.UserVO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@ToString
@Getter
public class AIVO {
    
    private final UUID id;
    private final String prompt;
    private final String answer;
    private final UserVO requestUser;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
    private final Integer createdBy;
    private final Integer updatedBy;
    private final Integer deletedBy;
    
}
