package com.meerkat.house.fut.service.social.oauth;

import com.meerkat.house.fut.model.account.AccountResponse;

public interface OauthService {
    AccountResponse upsertAccount(String tokenType, String accessToken);
}
