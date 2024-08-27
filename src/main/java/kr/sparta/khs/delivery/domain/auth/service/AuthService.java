package kr.sparta.khs.delivery.domain.auth.service;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import kr.sparta.khs.delivery.domain.user.service.UserService;
import kr.sparta.khs.delivery.domain.user.vo.UserVO;
import kr.sparta.khs.delivery.endpoint.dto.req.SignInRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.SignUpRequest;
import kr.sparta.khs.delivery.security.SecurityDetailsService;
import kr.sparta.khs.delivery.security.SecurityUserInfo;
import kr.sparta.khs.delivery.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    private final SecretKey secretKey;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityDetailsService securityDetailsService;
    private final JWTUtil jwtUtil;


    /**
     * 사용자 등록
     *
     * @param user 사용자 정보
     * @return 저장된 사용자
     */
    public UserVO signUp(SignUpRequest request) {
        //TODO change dto

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        UserVO result = userService.entry(request);

        return result;
    }

    /**
     * 사용자 인증
     *
     * @param userId 사용자 ID
     * @param password 비밀번호
     * @return JWT 액세스 토큰
     */
    public UserVO signIn(SignInRequest request) {
        UserVO userVO = userService.findByUsername(request.getUserId());

        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!passwordEncoder.matches(request.getPassword(), userVO.getPassword())) {
            throw new IllegalArgumentException("Invalid user ID or password");
        }

        return userVO;

    }
}