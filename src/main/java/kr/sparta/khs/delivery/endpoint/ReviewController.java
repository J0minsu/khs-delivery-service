package kr.sparta.khs.delivery.endpoint;

import jakarta.validation.Valid;
import kr.sparta.khs.delivery.domain.ai.vo.AIVO;
import kr.sparta.khs.delivery.domain.review.service.ReviewService;
import kr.sparta.khs.delivery.domain.review.vo.ReviewVO;
import kr.sparta.khs.delivery.domain.user.service.UserService;
import kr.sparta.khs.delivery.endpoint.dto.req.ReviewModifyRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.ReviewRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.SortStandard;
import kr.sparta.khs.delivery.endpoint.dto.res.AIResponse;
import kr.sparta.khs.delivery.endpoint.dto.res.ReviewResponse;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import kr.sparta.khs.delivery.security.SecurityUserInfo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity createReview(
            @AuthenticationPrincipal SecurityUserDetails userDetails,
            @Valid @RequestBody ReviewRequest reviewRequest,
            BindingResult bindingResult
    ) {

        if(bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Rating is out of range");
        }

        ReviewVO review = reviewService.createReview(userDetails.getId(), reviewRequest);

        ReviewResponse result = toResponse(review);

        return ResponseEntity.ok(result);

    }


    @GetMapping("/users/{userId}")
    public ResponseEntity getUsersReviews(
            @PathVariable Integer userId,
            @AuthenticationPrincipal SecurityUserDetails userDetails
            , Pageable pageable) {
        Page<ReviewVO> reviews = reviewService.getReviews(userId, pageable);

        Page<ReviewResponse> result = reviews.map(this::toResponse);

        return ResponseEntity.ok(result);

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    public ResponseEntity search(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "CREATED_DESC") SortStandard sort) {

        Page<ReviewVO> reviews =  reviewService.findReview(
                keyword, PageRequest.of(pageNumber, size, sort.getSort())
        );

        Page<ReviewResponse> result = reviews.map(this::toResponse);

        return ResponseEntity.ok(result);

    }

    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity getRestaurantReviews(
            UUID restaurantId,
            Pageable pageable
    ) {
        Page<ReviewVO> reviews = reviewService.getReviewsByRestaurantID(restaurantId, pageable);

        Page<ReviewResponse> result = reviews.map(this::toResponse);

        return ResponseEntity.ok(result);

    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity modifyReview(
            @PathVariable UUID reviewId,
            @AuthenticationPrincipal SecurityUserDetails userDetails,
            @Valid @RequestBody ReviewModifyRequest request,
            BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Rating is out of range");
        }

        ReviewVO review = reviewService.modifyReview(reviewId, userDetails.getId(), request);

        ReviewResponse result = toResponse(review);

        return ResponseEntity.ok(result);

    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity deleteReview(
            @PathVariable UUID reviewId,
            @AuthenticationPrincipal SecurityUserDetails userDetails) {

        reviewService.delete(reviewId, userDetails.getId());

        return ResponseEntity.noContent().build();

    }

    public ReviewResponse toResponse(ReviewVO review) {
        return new ReviewResponse(
                review.getId(),
                review.getComment(), review.getRating(),
                review.getReviewer().getName(), review.getCreatedAt());
    }



}
