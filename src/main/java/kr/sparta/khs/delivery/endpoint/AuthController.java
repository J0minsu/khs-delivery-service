package kr.sparta.khs.delivery.endpoint;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.sparta.khs.delivery.config.holder.Result;
import kr.sparta.khs.delivery.domain.auth.service.AuthService;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.vo.UserVO;
import kr.sparta.khs.delivery.endpoint.dto.req.SignInRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.SignUpRequest;
import kr.sparta.khs.delivery.util.JWTUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final JWTUtil jwtUtil;

    @PostMapping("/auth/signin")
    public ResponseEntity<Result<String>> createAuthenticationToken(
            @RequestBody SignInRequest request, HttpServletResponse response) {

        log.info("request :: {}", request);

        UserVO userVO = authService.signIn(request);

        String accessToken = jwtUtil.createAccessToken(userVO.getId(), userVO.getUsername(), userVO.getAuthType(), response);

        return ResponseEntity.ok().body(Result.success(accessToken));
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<Result<String>> signUp(
            @RequestBody @Valid SignUpRequest request,
            BindingResult bindingResult, HttpServletResponse response) {

        log.info("request :: {}", request);

        if(bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> {
                System.out.println("fieldError = " + fieldError);
            });
            throw new IllegalArgumentException("Argument is not valid");
        }

        UserVO userVO = authService.signUp(request);

        String accessToken = jwtUtil.createAccessToken(userVO.getId(), userVO.getUsername(), userVO.getAuthType(), response);

        log.info("sign-up is successfully :: {}", userVO.getUsername());

        return ResponseEntity.ok().body(Result.success(accessToken));
    }

}




