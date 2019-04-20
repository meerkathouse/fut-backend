package com.meerkat.house.fut.config;

import com.meerkat.house.fut.exception.Code;
import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.RestResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerConfig {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestResponse> globalException(RestException ex, HttpServletRequest request) {
        Code errorCode = ex.getErrorCode();

        RestResponse response = RestResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        Integer httpStatusCode = Integer.valueOf(String.valueOf(errorCode.getCode()).substring(0, 3));

        return new ResponseEntity<>(response, getHeaders(), HttpStatus.valueOf(httpStatusCode));
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        return headers;
    }
}
