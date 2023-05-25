package com.bside.gamjajeon.domain.user.entity;

import com.bside.gamjajeon.global.common.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Comment("로그인할 때 사용하는 사용자 이메일")
    @Column(nullable = false, unique = true)
    private String email;

    @Comment("사용자 이름")
    @Column(length = 10, nullable = false)
    private String username;

    @Comment("사용자 비밀번호")
    @Column(nullable = false)
    private String password;

    @Builder
    protected User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
