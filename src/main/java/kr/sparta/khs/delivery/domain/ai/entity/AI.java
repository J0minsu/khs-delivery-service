package kr.sparta.khs.delivery.domain.ai.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.ai.vo.AIVO;
import kr.sparta.khs.delivery.domain.common.entity.BaseEntity;
import kr.sparta.khs.delivery.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity(name = "p_ai_studio")
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Comment("AI 요청 관리")
public class AI extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Comment("AI 요청 아이디")
    private UUID id;

    @Column(nullable = false)
    @Comment("AI 요청 명령어")
    private String prompt;
    @Column(nullable = false, length = 50)
    @Comment("AI 응답 결과")
    private String answer;

    @JoinColumn(name = "request_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("AI 요청자")
    private User requestUser;


    protected AI(String prompt, String answer, User requestUser) {
        this.prompt = prompt;
        this.answer = answer;
        this.requestUser = requestUser;
    }

    public static AI create(String prompt, String answer, User requestUser) {
        return new AI(prompt, answer, requestUser);
    }

    public AIVO toVO() {
        return new AIVO(id,
                prompt, answer, requestUser.toUserVO(),
                getCreatedAt(), getUpdatedAt(), getDeletedAt(),
                getCreatedBy(), getUpdatedBy(), getDeletedBy());
    }

}
