package kr.sparta.khs.delivery.domain.user.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.common.entity.BaseEntity;
import kr.sparta.khs.delivery.domain.user.vo.UserVO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

@Entity(name = "p_user")
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Comment("사용자 관리")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("사용자 아이디")
    private int id;

    @Column(unique = true, nullable = false)
    @Comment("로그인 ID")
    private String username;
    @Column(nullable = false)
    @Comment("패스워드")
    private String password;

    @Column(nullable = false)
    @Comment("이름")
    private String name;

    @Column(nullable = false)
    @Comment("이메일")
    private String email;

    @Column(nullable = false)
    @Comment("연락처")
    private String contact;

    @Column(nullable = false)
    @Comment("주소")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    @Comment("계정 권한")
    private AuthType authType;

    @Comment("활성화 여부")
    private boolean isActive;

    protected User(String username, String password, String name, String email, String contact, String address, AuthType authType, boolean isActive) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.authType = authType;
        this.isActive = isActive;
    }

    public static User createUser(String username, String password, String name, String email, String contact, String address, AuthType authType, boolean isActive) {
        return new User(username, password, name, email, contact, address, authType, isActive);
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public UserVO toUserVO() {
        return new UserVO(
                id, username, password,
                name, email, contact, address,
                authType, isActive,
                getCreatedAt(), getUpdatedAt(), getDeletedAt(),
                getCreatedBy(), getUpdatedBy(), getDeletedBy());
    }

}
