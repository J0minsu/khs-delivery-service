package kr.sparta.khs.delivery.endpoint.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AIRequest {

    private Integer userId;
    @NotBlank
    private String prompt;

}
