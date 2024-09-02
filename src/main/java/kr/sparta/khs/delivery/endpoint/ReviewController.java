package kr.sparta.khs.delivery.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.sparta.khs.delivery.config.holder.Result;
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
@SecurityRequirement(name = "Bearer Authentication")
@SecurityScheme( name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
@Tag(name = "리뷰 API", description = "리뷰 관리 목적의 API Docs")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MASTER')")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<ReviewResponse>> createReview(
            @AuthenticationPrincipal SecurityUserDetails userDetails,
            @Valid @RequestBody ReviewRequest reviewRequest,
            BindingResult bindingResult
    ) {

        if(bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Rating is out of range");
        }

        ReviewVO review = reviewService.createReview(userDetails.getId(), reviewRequest);

        ReviewResponse result = toResponse(review);

        return ResponseEntity.ok(Result.success(result));

    }


    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MASTER')")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<Page<ReviewResponse>>> getUsersReviews(
            @PathVariable Integer userId,
            @AuthenticationPrincipal SecurityUserDetails userDetails
            , Pageable pageable) {
        Page<ReviewVO> reviews = reviewService.getReviews(userId, pageable);

        Page<ReviewResponse> result = reviews.map(this::toResponse);

        return ResponseEntity.ok(Result.success(result));

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MASTER')")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<Page<ReviewResponse>>> search(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "CREATED_DESC") SortStandard sort) {

        size = switch (size) {
            case 30 -> 30;
            case 50 -> 50;
            default -> 10;
        };

        Page<ReviewVO> reviews =  reviewService.findReview(
                keyword, PageRequest.of(pageNumber, size, sort.getSort())
        );

        Page<ReviewResponse> result = reviews.map(this::toResponse);

        return ResponseEntity.ok(Result.success(result));

    }

    @GetMapping("/restaurants/{restaurantId}")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<Page<ReviewResponse>>> getRestaurantReviews(
            UUID restaurantId,
            Pageable pageable
    ) {
        Page<ReviewVO> reviews = reviewService.getReviewsByRestaurantID(restaurantId, pageable);

        Page<ReviewResponse> result = reviews.map(this::toResponse);

        return ResponseEntity.ok(Result.success(result));

    }

    @PatchMapping("/{reviewId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MASTER')")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<ReviewResponse>> modifyReview(
            @PathVariable UUID reviewId,
            @AuthenticationPrincipal SecurityUserDetails userDetails,
            @Valid @RequestBody ReviewModifyRequest request,
            BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Rating is out of range");
        }

        ReviewVO review = reviewService.modifyReview(reviewId, userDetails.getId(), request);

        ReviewResponse result = toResponse(review);

        return ResponseEntity.ok(Result.success(result));

    }

    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MASTER')")
    @Operation(summary = "AI 생성", description = "AI 생성")
    public ResponseEntity<Result<Void>> deleteReview(
            @PathVariable UUID reviewId,
            @AuthenticationPrincipal SecurityUserDetails userDetails) {

        reviewService.delete(reviewId, userDetails.getId());

        return ResponseEntity.noContent().build();

    }

    public ReviewResponse toResponse(ReviewVO review) {
        return new ReviewResponse(
                review.getId(),
                review.getComment(), (double) review.getRating() / 2,
                review.getReviewer().getName(), review.getCreatedAt());
    }



}
