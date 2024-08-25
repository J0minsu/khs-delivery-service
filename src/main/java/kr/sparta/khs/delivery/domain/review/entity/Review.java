package kr.sparta.khs.delivery.domain.review.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "p_review")
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Comment("리뷰 관리")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Comment("리뷰 ID")
    private UUID id;

    @Column(length = 512)
    @Comment("리뷰 내용")
    private String comment;

    @Column(nullable = false)
    @Comment("리뷰 평점(0.5~5 0.5씩)")
    private BigDecimal rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    @Comment("리뷰 작성자 ID")
    private User reviewer;

    /**
     * TODO attach Order.class
     *  OneToOne(parent)
     */

}
