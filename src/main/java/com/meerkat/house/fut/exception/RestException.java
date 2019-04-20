package com.meerkat.house.fut.exception;

public class RestException extends RuntimeException {

    private Code errorCode;

    public RestException() { super(); }

    public RestException(Code errorCode) {
        this.errorCode = errorCode;
    }

    public Code getErrorCode() {
        return this.errorCode;
    }
}
