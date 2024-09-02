package kr.sparta.khs.delivery.endpoint.dto.res;

import kr.sparta.khs.delivery.domain.order.entity.Order;
import kr.sparta.khs.delivery.domain.user.vo.UserVO;
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
public class ReviewResponse {

    private UUID id;
    private String comment;
    private int rating;
    private String reviewerName;
    private LocalDateTime createdAt;

}
