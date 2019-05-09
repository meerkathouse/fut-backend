package com.meerkat.house.fut.service.social.oauth;

import com.meerkat.house.fut.exception.RestResponse;
import com.meerkat.house.fut.model.Account;

public interface OauthService {
    RestResponse upsertAccount(String tokenType, String accessToken);
}
