package kr.sparta.khs.delivery.endpoint.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import kr.sparta.khs.delivery.domain.order.entity.Order;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    private String comment;
    @Min(1) @Max(10)
    private int rating;
    private UUID orderId;

}
