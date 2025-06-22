package com.sukream.sukream.domains.user.domain.entity;

import com.sukream.sukream.commons.jpa.BaseTimeEntity;
import com.sukream.sukream.domains.user.domain.request.UserRequest;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Comment;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Entity
@Builder
@Comment("사용자 테이블")
public class Users extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    @Comment("사용자 이름")
    private String name;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    @Comment("사용자 이메일")
    private String email;

    @NotNull
    @Column(name = "phone_number", nullable = false, unique = true)
    @Comment("사용자 전화번호")
    private String phoneNumber;

    @NotNull
    @Column(name = "password", nullable = false)
    @Comment("사용자 비밀번호")
    private String password;

    @Column(name = "profile_image", nullable = true)
    @Comment("사용자 프로필 이미지")
    private String profileImage;

    @NotNull
    @Column(name = "is_active", nullable = false)
    @Comment("사용자 계정 활성화 여부")
    private boolean isActive;

    @Column(name = "oauth_provider")
    @Comment("OAuth 제공자 (GOOGLE, NAVER, KAKAO 등)")
    private String oauthProvider;

    @Column(name = "oauth_id")
    @Comment("OAuth 사용자 식별자")
    private String oauthId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.isActive = true;
    }


    public void updateUserInfo(UserRequest userRequest) {
        this.name = userRequest.getName();
        this.email = userRequest.getEmail();
        this.phoneNumber = userRequest.getPhoneNumber();
        this.password = userRequest.getPassword();
    }

    public void updateUserInfoExceptPassword(UserRequest userRequest) {
        this.name = userRequest.getName();
        this.email = userRequest.getEmail();
        this.phoneNumber = userRequest.getPhoneNumber();
    }


}
