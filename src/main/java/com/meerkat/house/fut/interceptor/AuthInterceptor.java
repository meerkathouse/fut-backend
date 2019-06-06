package com.meerkat.house.fut.interceptor;

import com.google.common.base.Strings;
import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.utils.FutConstant;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader(FutConstant.AUTHORIZATION);

        if(Strings.isNullOrEmpty(token)) {
            throw new RestException();
        }

        //  TODO.
        //  isUsable


        return false;
    }
}
