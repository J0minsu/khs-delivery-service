package kr.sparta.khs.delivery.util;

import kr.sparta.khs.delivery.security.SecurityUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    //todo
    // auth 리펙토링 -> SecurityUserInfo 대신 User 엔티티를 사용한다면 서비스단에서 db 조회없이 가능해짐
    public SecurityUserDetails getCurrentUserDetails() {
        return (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public int getCurrentUserId() {
        return getCurrentUserDetails().getSecurityUserInfo().getId();
    }
}