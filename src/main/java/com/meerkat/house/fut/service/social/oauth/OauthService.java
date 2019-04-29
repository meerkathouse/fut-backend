package com.meerkat.house.fut.service.social.oauth;

import com.meerkat.house.fut.model.Account;
import com.meerkat.house.fut.model.kakao.KakaoTokenResponse;

import java.io.UnsupportedEncodingException;

public interface OauthService {
    String getCodeUrl();
    Account getUserModel(String code);
    KakaoTokenResponse getTokenFromSocial(String code);
}
