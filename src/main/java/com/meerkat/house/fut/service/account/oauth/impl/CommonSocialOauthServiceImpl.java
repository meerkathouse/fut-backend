package com.meerkat.house.fut.service.account.oauth.impl;

import com.meerkat.house.fut.model.account.Account;
import com.meerkat.house.fut.service.account.oauth.CommonSocialOauthService;
import com.meerkat.house.fut.service.jwt.JwtServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Slf4j
@Service
public class CommonSocialOauthServiceImpl implements CommonSocialOauthService {

    private final JwtServiceImpl jwtServiceImpl;

    @Autowired
    public CommonSocialOauthServiceImpl(JwtServiceImpl jwtServiceImpl) {
        this.jwtServiceImpl = jwtServiceImpl;
    }

    @Override
    public String setSocialToken(String tokenType, String accessToken) {
        return new StringBuilder(tokenType)
                .append(" ")
                .append(accessToken)
                .toString();
    }

    @Override
    public String setJwtToken(Account account, Date expireTime) {
        return jwtServiceImpl.setJwtToken(account, expireTime);
    }
}
