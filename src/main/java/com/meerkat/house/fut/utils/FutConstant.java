package com.meerkat.house.fut.utils;

import java.util.Date;

public class FutConstant {
    //  social
    public static final String KAKAO = "kakao";

    //  header
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer";

    //  jwt
    public static final String JWT_ID = "id";
    public static final String JWT_EMAIL = "email";
    public static final String JWT_SOCIAL = "social";
    public static final String JWT_ROLE = "roles";

    public static final String JWT_ISS = "dev.meerkat.house";
    public static final String JWT_SUB = "meerkat.house.fut";

    public static final Long ACCESS_TOKEN_EXP_TIME = 1000 * 60 * 60 * 24L;
    public static final Long REFRESH_TOKEN_EXP_TIME = 1000 * 60 * 60 * 24 * 30L;

}
