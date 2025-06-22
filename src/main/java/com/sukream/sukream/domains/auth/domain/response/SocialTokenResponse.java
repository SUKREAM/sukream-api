package com.sukream.sukream.domains.auth.domain.response;

import lombok.Data;

@Data
public class SocialTokenResponse {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private String expires_in;
    private String scope;
}

