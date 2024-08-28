package kr.sparta.khs.delivery.domain.report.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.common.entity.BaseEntity;
import kr.sparta.khs.delivery.domain.report.vo.ReportVO;
import kr.sparta.khs.delivery.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Optional;
import java.util.UUID;

@Entity(name = "p_report")
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Comment("신고 관리")
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "report_id", updatable = false, nullable = false)
    @Comment("신고 아이디")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Comment("신고 종류")
    private ReportType reportType;

    @Column(name = "ref_id", nullable = false)
    @Comment("연관 테이블 아이디")
    private UUID referenceId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("신고 처리 현황(상태)")
    private ReportProcessStatus reportProcessStatus;

    @Column(nullable = false, length = 512)
    @Comment("신고 사유")
    private String reason;

    @Column(length = 512)
    @Comment("처리 답변")
    private String answer;

    @JoinColumn(name = "user_id", nullable = false
            , foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("신고자 ID")
    private User user;

    @JoinColumn(name = "report_handler_id", nullable = true
            , foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("문의 처리자 ID")
    private User reportHandler;

    protected Report(ReportType reportType, UUID referenceId, ReportProcessStatus reportProcessStatus, String reason, String answer, User user, User reportHandler) {
        this.reportType = reportType;
        this.referenceId = referenceId;
        this.reportProcessStatus = reportProcessStatus;
        this.reason = reason;
        this.answer = answer;
        this.user = user;
        this.reportHandler = reportHandler;
    }

    public static Report create(ReportType reportType, UUID referenceId, ReportProcessStatus reportProcessStatus, String reason, String answer, User user, User reportHandler) {
        return new Report(reportType, referenceId, reportProcessStatus, reason, answer, user, reportHandler);
    }

    public void solve(String answer, User reportHandler) {
        this.answer = answer;
        this.reportProcessStatus = ReportProcessStatus.FINISHED;
        this.reportHandler = reportHandler;
    }

    public void accept(User reportHandler) {
        this.reportProcessStatus = ReportProcessStatus.PROCESSING;
        this.reportHandler = reportHandler;
    }

    public ReportVO toVO() {

        User afterReportHandler = Optional.ofNullable(reportHandler).orElse(User.emptyObject());
        return new ReportVO(id, reportType, referenceId, reportProcessStatus, reason, answer,
                user.toUserVO(), afterReportHandler.toUserVO(),
                getCreatedAt(), getUpdatedAt(), getDeletedAt(),
                getCreatedBy(), getUpdatedBy(), getDeletedBy());
    }

}
