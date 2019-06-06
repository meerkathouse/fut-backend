package com.meerkat.house.fut.service.social.oauth;

import com.meerkat.house.fut.model.account.Account;
import com.meerkat.house.fut.model.account.AccountResponse;

public interface CommonSocialOauthService {
    String setSocialToken(String tokenType, String accessToken);
    AccountResponse setAccessToken(Account account);
}
