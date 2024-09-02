package kr.sparta.khs.delivery.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.sparta.khs.delivery.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter implements Ordered {

    private final JWTUtil jwtUtil;
    private final SecurityDetailsService jwtUserDetailsService;

    private static final String[] RESOURCE_WHITELIST = {
            "v3/api-docs", // v3 : SpringBoot 3(없으면 swagger 예시 api 목록 제공)
            "/swagger-ui/",
            "/swagger-resources/",
            "/webjars/",
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.equals("/auth/signin") || path.equals("/auth/signup")
            || Arrays.stream(RESOURCE_WHITELIST).anyMatch(path::contains)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtUtil.extractToken(request);

        if (token == null || ! jwtUtil.validateToken(token)) {
            response.setStatus(401);
            return;
        }

        Claims claims = jwtUtil.parsePayload(token);

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(String.valueOf(claims.get("username")));

        if (userDetails != null) {
            // jwt이므로 2번째 파라미터 credentials(password)를 null로 넘겨도 문제 없음(이미 앞 단계에서 인증 완료됨)
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}