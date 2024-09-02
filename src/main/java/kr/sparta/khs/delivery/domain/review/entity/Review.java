package kr.sparta.khs.delivery.domain.review.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.common.entity.BaseEntity;
import kr.sparta.khs.delivery.domain.order.entity.Order;
import kr.sparta.khs.delivery.domain.report.vo.ReportVO;
import kr.sparta.khs.delivery.domain.review.vo.ReviewVO;
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
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Comment("리뷰 ID")
    private UUID id;

    @Column(length = 512)
    @Comment("리뷰 내용")
    private String comment;

    @Column(nullable = false)
    @Comment("1 per 0.5 point max 10")
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    @Comment("리뷰 작성자 ID")
    private User reviewer;

    /**
     * TODO attach Order.class
     *  OneToOne(parent)
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    @Comment("주문 아이디")
    private Order order;

    protected Review(String comment, int rating, User reviewer, Order order) {
        this.comment = comment;
        this.rating = rating;
        this.reviewer = reviewer;
        this.order = order;
    }

    public static Review create(String comment, int rating, User reviewer, Order order) {
        if(rating <= 0 || rating > 10) {
            throw new IllegalArgumentException("rating is out of range(1 ~ 10)");
        }
        return new Review(comment, rating, reviewer, order);
    }

    public ReviewVO toVO() {
        return new ReviewVO(id, comment, rating, reviewer.toUserVO(), order,
                getCreatedAt(), getUpdatedAt(), getDeletedAt(),
                getCreatedBy(), getUpdatedBy(), getDeletedBy(), isDeleted());
    }

    public void modify(String comment, int rating) {
        this.comment = comment;
        this.rating = rating;
    }
}
