package kr.sparta.khs.delivery.domain.user.vo;

import kr.sparta.khs.delivery.domain.user.entity.AuthType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@ToString
public class UserVO {

    private final int id;
    private final String username;
    private final String password;
    private final String name;
    private final String email;
    private final String contact;
    private final String address;
    private final AuthType authType;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
    private final Integer createdBy;
    private final Integer updatedBy;
    private final Integer deletedBy;

    private boolean isDeleted;

}
