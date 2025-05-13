package com.sukream.sukream.domains.user.domain.entity;

import com.sukream.sukream.commons.jpa.BaseTimeEntity;
import com.sukream.sukream.domains.user.domain.enums.Gender;
import com.sukream.sukream.domains.user.domain.enums.SocialProvider;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Comment("소셜 계정 테이블")
public class SocialAccount extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "social_provider", nullable = false)
    @Comment("소셜계정 제공자")
    @Enumerated(EnumType.STRING)
    private SocialProvider socialProvider;

    @NotNull
    @Column(name = "social_provider_id", nullable = false)
    @Comment("소셜계정 제공자 고유 ID")
    private String socialProviderId;

    @NotNull
    @Column(name = "email", nullable = false)
    @Comment("소셜계정 제공자 이메일")
    private String email;

    @NotNull
    @Column(name = "gender")
    @Comment("소셜계정 사용자 성별")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Column(name = "birth")
    @Comment("소셜계정 사용자 생일")
    private LocalDate birth;

    @Builder(builderMethodName = "innerBuilder")
    private SocialAccount(
            User user,
            SocialProvider socialProvider,
            String socialProviderId,
            String email,
            Gender gender,
            LocalDate birth) {
        this.user = user;
        this.socialProvider = socialProvider;
        this.socialProviderId = socialProviderId;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
    }

}

