package kr.sparta.khs.delivery.domain.review.service;

import kr.sparta.khs.delivery.domain.order.entity.Order;
import kr.sparta.khs.delivery.domain.order.repository.OrderRepository;
import kr.sparta.khs.delivery.domain.review.entity.Review;
import kr.sparta.khs.delivery.domain.review.repository.ReviewRepository;
import kr.sparta.khs.delivery.domain.review.vo.ReviewVO;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import kr.sparta.khs.delivery.endpoint.dto.req.ReviewModifyRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.ReviewRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public ReviewVO createReview(Integer userId, ReviewRequest request) {

        User user = userRepository.getOne(userId);
        Order order = orderRepository.getOne(request.getOrderId());

        Review review = Review.create(request.getComment(), request.getRating(), user, order);

        Review savedReview = reviewRepository.save(review);

        return savedReview.toVO();
    }

    public ReviewVO getOrderReview(UUID orderId) {

        Review review = reviewRepository.findByOrderId(orderId)
                .orElseThrow(NoSuchElementException::new);

        return review.toVO();

    }

    public Page<ReviewVO> getReviews(Integer userId, Pageable pageable) {

        Page<Review> reviews = reviewRepository.findByReviewerId(userId, pageable);

        Page<ReviewVO> result = reviews.map(Review::toVO);
        return result;

    }

    public Page<ReviewVO> getReviewsByRestaurantID(UUID restaurantId, Pageable pageable) {

        Page<Review> reviews = reviewRepository.findByRestaurantId(restaurantId, pageable);

        Page<ReviewVO> result = reviews.map(Review::toVO);

        return result;

    }

    @Transactional
    public ReviewVO modifyReview(UUID reviewId, Integer userId, ReviewModifyRequest request) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("review not found"));

        User reviewer = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("user not found"));

        if(Objects.equals(review.getReviewer().getId(), reviewer.getId())) {
            throw new IllegalArgumentException("reviewer info is not correct");
        }

        review.modify(request.getComment(), request.getRating());

        return review.toVO();

    }

    @Transactional
    public void delete(UUID reviewId, Integer handlerId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("review not found"));

        review.delete(handlerId);

    }

    public Page<ReviewVO> findReview(String keyword, PageRequest pageRequest) {


        Page<Review> reports = reviewRepository.findByCommentContaining(keyword, pageRequest);

        Page<ReviewVO> result = reports.map(Review::toVO);

        return result;
    }
}
