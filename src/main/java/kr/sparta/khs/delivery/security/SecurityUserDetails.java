package kr.sparta.khs.delivery.security;

import kr.sparta.khs.delivery.domain.user.entity.AuthType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Slf4j
public class SecurityUserDetails implements UserDetails {

    private final SecurityUserInfo securityUserInfo;
    private final List<? extends GrantedAuthority> authorities;

    public SecurityUserDetails(SecurityUserInfo securityUserInfo, List<? extends GrantedAuthority> authorities) {
        this.securityUserInfo = securityUserInfo;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return securityUserInfo.getUsername();
    }

    @Override
    public String getPassword() {
        return securityUserInfo.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    public Integer getId() {
        return securityUserInfo.getId();
    }

    public String getName() {
        return securityUserInfo.getName();
    }

    public String getEmail() {
        return securityUserInfo.getEmail();
    }

    public String getContact() {
        return securityUserInfo.getContact();
    }

    public String getAddress() {
        return securityUserInfo.getAddress();
    }

    public AuthType getAuthType() {
        return securityUserInfo.getAuthType();
    }

    public boolean isActive() {
        return securityUserInfo.isActive();
    }


    /**
     * 필요시 재정의(기본 RETURN TRUE)
     */
    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE;
    }

}
