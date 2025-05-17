package com.sukream.sukream.domains.auth.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverOAuthResponse {
    private String id;
    private String nickname;
    private String profile_image;
    private String age;
    private String gender;
    private String email;
    private String mobile; // 핸드폰 번호
    private String name;
    private String birthday;
    private String birthyear;

}
