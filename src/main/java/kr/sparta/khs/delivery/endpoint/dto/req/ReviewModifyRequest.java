package kr.sparta.khs.delivery.endpoint.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewModifyRequest {

    private String comment;
    @Min(1) @Max(10)
    private int rating;
    private UUID orderId;

}
