package com.sukream.sukream.domains.auth.domain.response;

import com.sukream.sukream.commons.domain.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse extends Response {
    private String accessToken;
}
