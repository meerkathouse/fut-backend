package com.meerkat.house.fut.service.account.oauth;

import com.meerkat.house.fut.model.account.AccountResponse;

public interface OauthService {
    AccountResponse upsertAccount(String tokenType, String accessToken);
    AccountResponse findAccountByUid(Integer uid);
}
