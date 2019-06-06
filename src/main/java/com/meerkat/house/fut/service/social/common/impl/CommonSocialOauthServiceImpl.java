package com.meerkat.house.fut.service.social.common.impl;

import com.meerkat.house.fut.service.social.common.CommonSocialOauthService;
import org.springframework.stereotype.Service;

@Service
public class CommonSocialOauthServiceImpl implements CommonSocialOauthService {

    @Override
    public String setSocialToken(String tokenType, String accessToken) {
        return new StringBuilder(tokenType)
                .append(" ")
                .append(accessToken)
                .toString();
    }

    public String setAccessToken() {
        return null;
    }

    public void setSession() {

    }
}
