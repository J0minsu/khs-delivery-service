package kr.sparta.khs.delivery.domain.report.service;

import kr.sparta.khs.delivery.domain.ai.service.AIService;
import kr.sparta.khs.delivery.domain.ai.vo.AIVO;
import kr.sparta.khs.delivery.domain.report.entity.ReportProcessStatus;
import kr.sparta.khs.delivery.domain.report.entity.ReportType;
import kr.sparta.khs.delivery.domain.report.vo.ReportVO;
import kr.sparta.khs.delivery.domain.user.entity.AuthType;
import kr.sparta.khs.delivery.domain.user.service.UserService;
import kr.sparta.khs.delivery.domain.user.vo.UserVO;
import kr.sparta.khs.delivery.endpoint.dto.req.AIRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.ReportCreateRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.SignUpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
class ReportServiceTest {

    @Autowired private ReportService reportService;
    @Autowired private UserService userService;
    @Autowired private AIService aiService;

    private UserVO userVO;
    private AIVO aiVO;
    private ReportVO reportVO;

    @BeforeEach
    public void setUp() {
        userVO = userService.entry(signUpRequest());
        aiVO = aiService.create(getAIRequest());
        reportVO = reportService.createReport(getReportCreateRequest());
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

    public ReportCreateRequest getReportCreateRequest() {
        return new ReportCreateRequest(userVO.getId(), ReportType.AI, aiVO.getId(), "얘가 답변을 안 해요");
    }

    @Test
    public void 신고는_올바르게_생성돼야_한다() throws Exception {

        //given
        ReportCreateRequest request = getReportCreateRequest();

        //when
        ReportVO report = reportService.createReport(request);

        System.out.println("report = " + report);
        //then
        assertEquals(request.getReferenceId(), report.getReferenceId());

    }

    @Test
    public void 신고는_올바르게_접수될_수_있어야한다() throws Exception {

        //given
        ReportVO report = reportVO;

        SignUpRequest userRequest = signUpRequest();
        userRequest.setUsername("msjojj");
        UserVO handler = userService.entry(userRequest);

        //when
        ReportVO acceptReport = reportService.accept(report.getId(), handler.getId());

        //then
        assertEquals(report.getId(), acceptReport.getId());
        assertEquals(ReportProcessStatus.PROCESSING, acceptReport.getReportProcessStatus());

    }

    @Test
    public void 신고는_올바르게_삭제될_수_있어야한다() throws Exception {
    
        //given
        ReportVO report = reportVO;

        SignUpRequest userRequest = signUpRequest();
        userRequest.setUsername("msjojj");

        UserVO handler = userService.entry(userRequest);

        //when
        ReportVO deleted = reportService.delete(report.getId(), handler.getId());

        //then
        assertEquals(report.getId(), deleted.getId());
        assertEquals(handler.getId(), deleted.getDeletedBy());
    }
    
}