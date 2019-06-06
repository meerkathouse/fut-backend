package com.meerkat.house.fut.exception;

public enum ResultCode implements Code {
    SUCCESS(200, "SUCCESS"),
    BAD_REQUEST(4001001, "BAD_REQUEST"),
    BAD_REQUEST_CUSTOM(4001002, "BAD_REQUEST_CUSTOM"),
    KAKAO_HTTP_ERROR(4001003, "KAKAO_HTTP_ERROR"),

    AUTH_ERROR(4011001, "AUTH_ERROR"),
    AUTH_TOKEN_IS_NULL(4011002, "Token is null or empty"),
    AUTH_TOKEN_TYPE_IS_INVALID(4011003, "Token type is invalid"),
    AUTH_TOKEN_IS_INVALID(4011004, "Token is invalid"),

    FORBIDDEN(4031001, "FORBIDDEN"),
    INVALID_SOCIAL(4031002, "INVALID_SOCIAL"),

    NOT_FOUND(4041001, "NOT_FOUND"),
    INFORMATION_INSUFFICIENT(4041002, "INFORMATION_INSUFFICIENT"),
    KAKAO_RESPONSE_NOT_FOUND(4041003, "KAKAO_RESPONSE_NOT_FOUND"),
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
