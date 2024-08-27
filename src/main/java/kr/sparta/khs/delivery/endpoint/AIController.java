package kr.sparta.khs.delivery.endpoint;

import kr.sparta.khs.delivery.domain.ai.service.AIService;
import kr.sparta.khs.delivery.domain.ai.vo.AIVO;
import kr.sparta.khs.delivery.domain.review.service.ReviewService;
import kr.sparta.khs.delivery.domain.user.service.UserService;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;
    private final UserService userService;


    @GetMapping("/ais")
    public ResponseEntity findByUser(@AuthenticationPrincipal SecurityUserDetails userDetails) {

        Page<AIVO> result = aiService.findByUser(userDetails.getId());

        return ResponseEntity.ok(result);
    }

}
