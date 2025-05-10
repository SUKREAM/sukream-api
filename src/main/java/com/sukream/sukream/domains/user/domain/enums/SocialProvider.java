package com.sukream.sukream.domains.user.domain.enums;

import org.hibernate.annotations.Comment;

@Comment("소셜 계정 제공자")
public enum SocialProvider {
    GOOGLE,
    LOCAL,
}
