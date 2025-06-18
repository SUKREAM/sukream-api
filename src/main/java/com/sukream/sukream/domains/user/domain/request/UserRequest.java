package com.sukream.sukream.domains.user.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserRequest {
    @Schema(title = "사용자 이름", example = "정다연")
    private String name;

    @Schema(title = "사용자 이메일", example = "jung@example.com")
    private String email;

    @Schema(title = "사용자 전화번호", example = "010-0000-0000")
    private String phoneNumber;

    @Schema(title = "사용자 비밀번호", example = "djio39fv")
    private String password;

    @Schema(title = "사용자 프로필 이미지", example = "https://example.com")
    private String profileImage;

}
