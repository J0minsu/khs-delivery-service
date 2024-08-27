package kr.sparta.khs.delivery.domain.user.mapper;


import kr.sparta.khs.delivery.domain.user.vo.UserVO;
import kr.sparta.khs.delivery.security.SecurityUserInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

    public static SecurityUserInfo toSecurityUserInfo(UserVO user) {
        return new SecurityUserInfo(
                user.getId(), user.getUsername(), user.getPassword(), user.getName(),
                user.getEmail(), user.getContact(), user.getAddress(),
                user.getAuthType(), user.isActive()
        );
    }



}
