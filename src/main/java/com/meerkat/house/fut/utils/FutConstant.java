package com.meerkat.house.fut.utils;

import java.util.Date;

public class FutConstant {
    //  account
    public static final String KAKAO = "kakao";

    //  header
    public static final String AUTHORIZATION = "Authorization";
    public static final String UID = "uid";
    public static final String BEARER = "Bearer";

    //  jwt
    public static final String JWT_ID = "id";
    public static final String JWT_EMAIL = "email";
    public static final String JWT_SOCIAL = "account";
    public static final String JWT_ROLE = "roles";

    public static final String JWT_ISS = "dev.meerkat.house";
    public static final String JWT_SUB = "meerkat.house.fut";

    public static final Long ACCESS_TOKEN_EXP_TIME = 1000 * 60 * 60 * 24L;
    public static final Long REFRESH_TOKEN_EXP_TIME = 1000 * 60 * 60 * 24 * 30L;


    //  team member
    public static final String APPROVING = "APPROVING";
    public static final String APPROVED = "APPROVED";
    public static final String REJECT = "REJECT";

    //  match
    public static final Integer DRAW = 0;
    public static final Integer HOME_WIN = 1;
    public static final Integer AWAY_WIN = 2;

    public static final String BEFORE = "BEFORE";
    public static final String PLAYING = "PLAYING";
    public static final String END = "END";

    //  match vote
    public static final String ATTEND = "ATTEND";
    public static final String NOT_ATTEND = "NOT_ATTEND";
    public static final String NO_DECISION = "NO_DECISION";
}
