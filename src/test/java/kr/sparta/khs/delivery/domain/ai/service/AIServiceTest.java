package kr.sparta.khs.delivery.domain.ai.service;

import kr.sparta.khs.delivery.domain.ai.entity.AI;
import kr.sparta.khs.delivery.domain.ai.vo.AIVO;
import kr.sparta.khs.delivery.domain.user.entity.AuthType;
import kr.sparta.khs.delivery.domain.user.service.UserService;
import kr.sparta.khs.delivery.domain.user.vo.UserVO;
import kr.sparta.khs.delivery.endpoint.dto.req.AIRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.SignUpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
class AIServiceTest {

    @Autowired AIService aiService;
    @Autowired UserService userService;

    UserVO userVO;
    AIVO aiVO;

    @BeforeEach
    public void setUp() {
        userVO = userService.entry(signUpRequest());
        aiVO = aiService.create(getAIRequest());
    }

    public SignUpRequest signUpRequest() {

        SignUpRequest result = SignUpRequest.builder()
                .username("msjo")
                .password("password")
                .name("조민수")
                .email("msjo@gmail.com")
                .contact("010-5525-1125")
                .address("성남시 분당구 판교로 1663 이지더원 103동 702호")
                .authType(AuthType.CUSTOMER)
                .build();

        return result;
    }

    public AIRequest getAIRequest() {
        AIRequest aiRequest = new AIRequest(userVO.getId(), "카레 제육 돈까스 세 음식을 한 번에 제공하는 메뉴의 이름을 추천해줘!");
        return aiRequest;
    }

    @Test
    public void AI생성은_올바르게_수행돼야_한다() throws Exception {

        //given
        AIRequest aiRequest = getAIRequest();

        //when
        AIVO aiVO = aiService.create(aiRequest);

        AIVO result = aiService.findById(aiVO.getId());

        //then
        assertEquals(aiRequest.getUserId(), result.getRequestUser().getId());
    }

}