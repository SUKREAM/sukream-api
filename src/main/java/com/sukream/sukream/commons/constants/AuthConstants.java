package com.sukream.sukream.commons.constants;

public class AuthConstants {
    public static final String AUTHORIZATION = "Authorization";
    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String USER = "USER";
    public static final String BEARER = "Bearer ";
    // 엑세스 토큰 String
    public static final String ACCESS_TOKEN_STR = "accessToken";

    // 리프레시 토큰 String
    public static final String REFRESH_TOKEN_STR = "refreshToken";

    // naver
    public static final String NAVER = "naver";
    public static final String NAVER_VALIDATE_URL = "https://openapi.naver.com/v1/nid/me";
    public static final String NAVER_TOKEN_URL = "https://nid.naver.com/oauth2.0/token";

    // google
    public static final String GOOGLE = "google";
    public static final String GOOGLE_TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";
    public static final String GOOGLE_VALIDATE_URL = "https://www.googleapis.com/oauth2/v1/userinfo";

    // kakao
    public static final String KAKAO = "kakao";
    public static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    public static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";



    public static final String GRANT_TYPE = "grant_type";
    public static final String CODE = "code";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String AUTHENTICATION_CODE = "authorization_code";

}

