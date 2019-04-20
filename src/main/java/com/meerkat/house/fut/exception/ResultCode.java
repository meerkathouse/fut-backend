package com.meerkat.house.fut.exception;

public enum ResultCode implements Code {
    SUCCESS(200, "SUCCESS"),
    BAD_REQUEST(4001001, "BAD_REQUEST"),
    BAD_REQUEST_CUSTOM(4001002, "BAD_REQUEST_CUSTOM"),

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
