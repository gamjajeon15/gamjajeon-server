package com.bside.gamjajeon.domain.user.entity;

import com.bside.gamjajeon.global.common.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Comment("사용자 이메일")
    @Column(nullable = false, unique = true)
    private String email;

    @Comment("로그인할 때 사용하는 ID")
    @Column(length = 32, unique = true, nullable = false)
    private String username;

    @Comment("사용자 비밀번호")
    @Column(nullable = false)
    private String password;

    @Comment("마케팅 정보 수신 동의 여부 (0: 미동의, 1:동의)")
    @ColumnDefault("1")
    @Column(nullable = false)
    private int adStatus;

    @Builder
    protected User(String email, String username, String password, int adStatus) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.adStatus = adStatus;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
