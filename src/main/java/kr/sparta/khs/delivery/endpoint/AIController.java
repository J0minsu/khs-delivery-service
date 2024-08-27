package kr.sparta.khs.delivery.endpoint;

import kr.sparta.khs.delivery.domain.ai.service.AIService;
import kr.sparta.khs.delivery.domain.ai.vo.AIVO;
import kr.sparta.khs.delivery.domain.review.service.ReviewService;
import kr.sparta.khs.delivery.domain.user.service.UserService;
import kr.sparta.khs.delivery.endpoint.dto.req.AIRequest;
import kr.sparta.khs.delivery.endpoint.dto.res.AIResponse;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ais")
public class AIController {

    private final AIService aiService;
    private final UserService userService;


    @GetMapping
    public ResponseEntity findByUser(@AuthenticationPrincipal SecurityUserDetails userDetails) {

        Page<AIVO> aiResultList = aiService.findByUser(userDetails.getId());

        Page<AIResponse> result = aiResultList.map(this::toAIResponse);

        return ResponseEntity.ok(result);

    }

    @PostMapping
    public ResponseEntity requestAI(@AuthenticationPrincipal SecurityUserDetails userDetails,
                                    @RequestBody AIRequest request) {

        request.setUserId(userDetails.getId());

        AIVO ai = aiService.create(request);

        AIResponse result = toAIResponse(ai);

        return ResponseEntity.ok(result);
    }



    public AIResponse toAIResponse(AIVO aiVO) {
        return new AIResponse(aiVO.getId(), aiVO.getPrompt(), aiVO.getAnswer(), aiVO.getCreatedAt());
    }
}
