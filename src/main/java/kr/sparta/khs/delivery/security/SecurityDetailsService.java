package kr.sparta.khs.delivery.security;

import kr.sparta.khs.delivery.domain.user.service.UserService;
import kr.sparta.khs.delivery.domain.user.vo.UserVO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityDetailsService implements UserDetailsService {

    UserService userService;


    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        SecurityUserInfo securityUserInfo = getSecurityUserInfo(id);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(securityUserInfo.getAuthType().name()));
        return new SecurityUserDetails(securityUserInfo, authorities);
    }

    private SecurityUserInfo getSecurityUserInfo(String username) throws UsernameNotFoundException {

        UserVO user = userService.findByUsername(username);

        if(user.isDeleted()) {
            throw new UsernameNotFoundException("This account is unavailable");
        }

        SecurityUserInfo securityUserInfo = toSecurityUserInfo(user);

        return securityUserInfo;

    }

    public SecurityUserInfo toSecurityUserInfo(UserVO user) {
        return new SecurityUserInfo(
                user.getId(), user.getUsername(), user.getPassword(), user.getName(),
                user.getEmail(), user.getContact(), user.getAddress(),
                user.getAuthType(), !user.isDeleted()
        );
    }

}
