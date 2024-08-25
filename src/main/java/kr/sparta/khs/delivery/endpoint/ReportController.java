package kr.sparta.khs.delivery.endpoint;

import kr.sparta.khs.delivery.domain.report.service.ReportService;
import kr.sparta.khs.delivery.domain.review.service.ReviewService;
import kr.sparta.khs.delivery.domain.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;

}
