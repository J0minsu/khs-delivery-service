package kr.sparta.khs.delivery.domain.review.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kr.sparta.khs.delivery.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Optional<Review> findByOrderId(UUID orderId);

    Page<Review> findByReviewerId(Integer userId, Pageable pageable);

    @EntityGraph(attributePaths = {"order"})
    @Query("""
        SELECT review
          FROM kr.sparta.khs.delivery.domain.review.entity.Review review
           JOIN FETCH review.order order
           JOIN FETCH order.restaurant restaurant
         WHERE restaurant.id = :restaurantId
    """)
    Page<Review> findByRestaurantId(@Param("restaurantId") UUID restaurantId, Pageable pageable);

    /*@Query("""
        SELECT review
          FROM kr.sparta.khs.delivery.domain.review.entity.Review review
         WHERE review.comment = :keyword
    """)*/
    Page<Review> findByCommentContaining(String keyword, PageRequest pageRequest);
}
