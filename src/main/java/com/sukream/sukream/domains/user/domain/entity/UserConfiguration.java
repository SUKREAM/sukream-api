package com.sukream.sukream.domains.user.domain.entity;

import com.sukream.sukream.commons.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Comment;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Entity
@Builder
@Comment("사용자 설정 테이블")
public class UserConfiguration extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "refresh_token", length = 500)
    @Comment("사용자 리프레시 토큰")
    private String refreshToken;

    @NotNull
    @Column(name = "privacy_policy", nullable = false)
    @Comment("개인정보 처리방침 동의 여부")
    private boolean privacyPolicy;

    @NotNull
    @Column(name = "email_subscription", nullable = false)
    @Comment("이메일 수신 동의 여부")
    private boolean emailSubscription;

    @NotNull
    @Column(name = "marketing_constent", nullable = false)
    @Comment("마케팅 정보 수신 동의 여부")
    private boolean marketingConsent;

    @NotNull
    @Column(name = "push_notification", nullable = false)
    @Comment("푸시 알림 활성화 여부")
    private boolean pushNotification;

    @PrePersist
    protected void onCreate() {
        this.pushNotification = false;
        this.marketingConsent = false;
        this.privacyPolicy = false;
        this.emailSubscription = false;
    }
}
