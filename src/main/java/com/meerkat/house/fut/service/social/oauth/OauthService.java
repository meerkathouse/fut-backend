package com.meerkat.house.fut.service.social.oauth;

import com.meerkat.house.fut.model.account.Account;

public interface OauthService {
    Account upsertAccount(String tokenType, String accessToken);
}
