package kr.sparta.khs.delivery.endpoint.dto.req;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kr.sparta.khs.delivery.domain.user.entity.AuthType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class SignUpRequest {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]+$")
    private String username;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[\\W_]).+$")
    private String password;
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    private String email;
    private String contact;
    private String address;
    private AuthType authType;
    private boolean isActive;

}
