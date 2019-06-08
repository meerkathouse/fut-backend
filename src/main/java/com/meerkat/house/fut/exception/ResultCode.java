package com.meerkat.house.fut.exception;

public enum ResultCode implements Code {
    SUCCESS(200, "SUCCESS"),
    SUCCESS_TEAM_CREATE(2001001, "Success create team"),
    SUCCESS_TEAM_DELETE(2001002, "Success delete team"),

    BAD_REQUEST(4001001, "BAD_REQUEST"),
    BAD_REQUEST_CUSTOM(4001002, "BAD_REQUEST_CUSTOM"),
    KAKAO_HTTP_ERROR(4001003, "KAKAO_HTTP_ERROR"),
    TEAM_ALREADY_EXIST(4001004, "Already exist team"),

    AUTH_ERROR(4011001, "AUTH_ERROR"),
    AUTH_TOKEN_IS_NULL(4011002, "Token is null or empty"),
    AUTH_TOKEN_TYPE_IS_INVALID(4011003, "Token type is invalid"),
    AUTH_TOKEN_IS_INVALID(4011004, "Token is invalid"),

    FORBIDDEN(4031001, "FORBIDDEN"),
    INVALID_SOCIAL(4031002, "INVALID_SOCIAL"),
    INVALID_UID(4031003, "Invalid uid"),

    NOT_FOUND(4041001, "NOT_FOUND"),
    INFORMATION_INSUFFICIENT(4041002, "INFORMATION_INSUFFICIENT"),
    KAKAO_RESPONSE_NOT_FOUND(4041003, "KAKAO_RESPONSE_NOT_FOUND"),
    TEAM_NAME_IS_NULL(4041004, "Team name is null or empty"),
    TID_IS_NULL(4041005, "Tid is null"),
    TEAM_NOT_FOUND(4041006, "Team not found"),

    NO_NAMING(5001001, "NO_NAMING");

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
