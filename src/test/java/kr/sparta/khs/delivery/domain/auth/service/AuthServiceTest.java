package kr.sparta.khs.delivery.domain.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import kr.sparta.khs.delivery.domain.user.entity.AuthType;
import kr.sparta.khs.delivery.domain.user.service.UserService;
import kr.sparta.khs.delivery.domain.user.vo.UserVO;
import kr.sparta.khs.delivery.endpoint.dto.req.SignUpRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.SortStandard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@Transactional(readOnly = true)
class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

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

    @Test
    public void 회원가입은_올바르게_수행돼야_한다() throws Exception {

        //given
        SignUpRequest request = signUpRequest();
        //when
        UserVO userVO = authService.signUp(request);
        //then
        System.out.println("userVO = " + userVO);
        Assertions.assertEquals(request.getName(), userVO.getName());

    }

    @Test
    public void 서치_동작_테스트() throws Exception {

        //given
        userService.findUsers("helloworkd", PageRequest.of(0, 10, SortStandard.CREATED_DESC.getSort()));
        //when

        //then

    }

}