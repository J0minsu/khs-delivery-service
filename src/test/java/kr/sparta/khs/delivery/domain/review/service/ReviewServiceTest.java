package kr.sparta.khs.delivery.domain.review.service;

import kr.sparta.khs.delivery.endpoint.dto.req.SortStandard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
class ReviewServiceTest {

    @Autowired private ReviewService reviewService;


    @Test
    public void 일반고객은_가게로만_리뷰조회가_가능해야한다() throws Exception {

        //given
        reviewService.getReviewsByRestaurantID(UUID.randomUUID(), PageRequest.of(1, 10));
        //when

        //then

    }

    @Test
    public void 서치_동작_확인() throws Exception {

        reviewService.findReview("helloWorld", PageRequest.of(1, 10, SortStandard.CREATED_DESC.getSort()));

        //given

        //when

        //then

    }
}