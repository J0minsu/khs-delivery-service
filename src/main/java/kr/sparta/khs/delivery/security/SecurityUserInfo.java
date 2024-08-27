package kr.sparta.khs.delivery.security;

import kr.sparta.khs.delivery.domain.user.entity.AuthType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityUserInfo {

    private int id;
    private String username;
    private String password;

    private String name;
    private String email;
    private String contact;
    private String address;

    private AuthType authType;
    private boolean isActive;

}