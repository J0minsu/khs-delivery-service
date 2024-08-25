package kr.sparta.khs.delivery.config;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditingConfig implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        //TODO auditor security info 에서 추출
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return Optional.empty();
    }

}
