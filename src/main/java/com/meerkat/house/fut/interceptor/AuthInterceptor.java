package com.meerkat.house.fut.interceptor;

import com.google.common.base.Strings;
import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.ResultCode;
import com.meerkat.house.fut.service.jwt.JwtServiceImpl;
import com.meerkat.house.fut.utils.FutConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtServiceImpl jwtServiceImpl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader(FutConstant.AUTHORIZATION);
        if(Strings.isNullOrEmpty(token)) {
            throw new RestException(ResultCode.AUTH_TOKEN_IS_NULL);
        }

        if(token.split(" ").length < 2) {
            throw new RestException(ResultCode.AUTH_TOKEN_IS_INVALID);
        }

        String tokenType = token.split(" ")[0];
        String accessToken = token.split(" ")[1];

        if(Strings.isNullOrEmpty(accessToken)) {
            log.error("[AUTH] Access token is null.");
            throw new RestException(ResultCode.AUTH_TOKEN_IS_INVALID);
        }

        if(Strings.isNullOrEmpty(tokenType) || !FutConstant.BEARER.equalsIgnoreCase(tokenType)) {
            log.error("[AUTH] Token type is null. Or not Bearer");
            throw new RestException(ResultCode.AUTH_TOKEN_TYPE_IS_INVALID);
        }

        return jwtServiceImpl.isUsable(accessToken) ? true : false;
    }
}
