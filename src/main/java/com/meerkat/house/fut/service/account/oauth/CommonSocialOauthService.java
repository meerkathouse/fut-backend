package com.meerkat.house.fut.service.account.oauth;

import com.meerkat.house.fut.model.account.Account;

import java.util.Date;

public interface CommonSocialOauthService {
    String setSocialToken(String tokenType, String accessToken);
    String setJwtToken(Account account, Date expireTime);
}
