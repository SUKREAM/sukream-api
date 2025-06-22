package com.sukream.sukream.commons.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SocialOAuthProviderConfig {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String tokenUrl;
    private String userInfoUrl;
}

