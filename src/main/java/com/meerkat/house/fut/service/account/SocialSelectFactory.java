package com.meerkat.house.fut.service.account;

import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.ResultCode;
import com.meerkat.house.fut.service.account.oauth.OauthService;
import com.meerkat.house.fut.service.account.oauth.impl.KakaoOauthServiceImpl;
import com.meerkat.house.fut.utils.FutConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialSelectFactory {

    private KakaoOauthServiceImpl kakaoOauthServiceImpl;

    @Autowired
    public SocialSelectFactory(KakaoOauthServiceImpl kakaoOauthServiceImpl) {
        this.kakaoOauthServiceImpl = kakaoOauthServiceImpl;
    }

    public OauthService getSocialOauthService(String social) {
        switch(social.toLowerCase()) {
            case FutConstant.KAKAO:
                return kakaoOauthServiceImpl;
        }
        throw new RestException(ResultCode.INVALID_SOCIAL);
    }
}
