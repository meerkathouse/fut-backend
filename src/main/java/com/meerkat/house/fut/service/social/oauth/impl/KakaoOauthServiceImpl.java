package com.meerkat.house.fut.service.social.oauth.impl;

import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.ResultCode;
import com.meerkat.house.fut.model.kakao.KakaoTokenResponse;
import com.meerkat.house.fut.model.UserModel;
import com.meerkat.house.fut.model.kakao.user.KakaoUserResponse;
import com.meerkat.house.fut.service.social.oauth.OauthService;
import com.meerkat.house.fut.utils.FutConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;

@Service
@Slf4j
public class KakaoOauthServiceImpl implements OauthService {

    @Value("${socials.kakao.host}")
    private String kakaoUrl;

    @Value("${socials.kakao.oauth_api}")
    private String oauthApi;

    @Value("${socials.kakao.client_id}")
    private String clientId;

    @Value("${socials.kakao.client_secret}")
    private String clientSecret;

    @Value("${socials.kakao.redirect_uri}")
    private String redirectUri;

    @Value("${socials.kakao.grant_type}")
    private String grantType;

    @Value("${socials.kakao.token_api}")
    private String tokenApi;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getCodeUrl() {
        String url = new StringBuilder(kakaoUrl)
                .append(oauthApi)
                .append("?client_id=")
                .append(clientId)
                .append("&redirect_uri=")
                .append(URLEncoder.encode(redirectUri))
                .append("&response_type=code")
                .toString();

        return url;
    }

    @Override
    public UserModel getUserModel(String code) {
        KakaoTokenResponse kakaoTokenResponse = this.getAccessToken(code);

        //  TODO. upsert token to database

        String accessToken = new StringBuilder(kakaoTokenResponse.getTokenType())
                .append(" ")
                .append(kakaoTokenResponse.getAccessToken())
                .toString();

        String url = new StringBuilder("https://kapi.kakao.com")
                .append("/v2/user/me")
                .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(FutConstant.AUTHORIZATION, accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserResponse> response = null;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, KakaoUserResponse.class);
        } catch (HttpClientErrorException | ResourceAccessException e) {
            log.error("kakao get user : http client error");
            throw new RestException(ResultCode.NO_NAMING);
        }

        if(null == response || null == response.getBody()) {
            log.error("kakao get user : response or response.body is null");
            throw new RestException(ResultCode.NO_NAMING);
        }

        KakaoUserResponse kakaoUserResponse = response.getBody();

        //  TODO. upsert user model
        UserModel userModel = UserModel.builder()
                .id(kakaoUserResponse.getId())
                .nickname(kakaoUserResponse.getProperties().getNickname())
                .imageUrl(kakaoUserResponse.getProperties().getThumbnailImage())
                .social(FutConstant.KAKAO)
                .build();

        return userModel;
    }

    @Override
    public KakaoTokenResponse getAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", grantType);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        String url = new StringBuilder(kakaoUrl)
                .append(tokenApi)
                .toString();

        ResponseEntity<KakaoTokenResponse> response = null;

        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, KakaoTokenResponse.class);
        } catch (HttpClientErrorException | ResourceAccessException e) {
            log.error("kakao token response : http client error");
            throw new RestException(ResultCode.NO_NAMING);
        }

        if (null == response || null == response.getBody()) {
            log.error("kakao token response : response or response.body is null");
            throw new RestException(ResultCode.NO_NAMING);
        }

        return response.getBody();
    }
}
