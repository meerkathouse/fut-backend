package com.meerkat.house.fut.service.social.oauth;

import com.meerkat.house.fut.model.account.Account;
import com.meerkat.house.fut.model.account.AccountResponse;

import java.util.Date;

public interface CommonSocialOauthService {
    String setSocialToken(String tokenType, String accessToken);
    String setJwtToken(Account account, Date expireTime);
}
