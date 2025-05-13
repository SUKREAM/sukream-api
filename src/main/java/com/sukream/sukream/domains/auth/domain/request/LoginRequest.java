package com.sukream.sukream.domains.auth.domain.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;

    private String pw;
}