package com.meerkat.house.fut.service.social.oauth;

import com.meerkat.house.fut.model.kakao.KakaoTokenResponse;
import com.meerkat.house.fut.model.UserModel;

public interface OauthService {
    String getCodeUrl();
    UserModel getUserModel(String code);
    KakaoTokenResponse getAccessToken(String code);
}
