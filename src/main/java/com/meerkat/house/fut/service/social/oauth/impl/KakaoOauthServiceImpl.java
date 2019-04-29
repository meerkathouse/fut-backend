package com.meerkat.house.fut.service.social.oauth.impl;

import com.google.common.base.Strings;
import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.ResultCode;
import com.meerkat.house.fut.model.Account;
import com.meerkat.house.fut.model.kakao.KakaoTokenResponse;
import com.meerkat.house.fut.model.kakao.user.KakaoUserResponse;
import com.meerkat.house.fut.repository.AccountRepository;
import com.meerkat.house.fut.service.social.oauth.OauthService;
import com.meerkat.house.fut.utils.FutConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
@Slf4j
@Transactional
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


    private RestTemplate restTemplate;
    private AccountRepository accountRepository;

    @Autowired
    public KakaoOauthServiceImpl(RestTemplate restTemplate, AccountRepository accountRepository) {
        this.restTemplate = restTemplate;
        this.accountRepository = accountRepository;
    }

    @Override
    public String getCodeUrl() {
        String url = null;

        try {
            url = new StringBuilder(kakaoUrl)
                    .append(oauthApi)
                    .append("?client_id=")
                    .append(clientId)
                    .append("&redirect_uri=")
                    .append(URLEncoder.encode(redirectUri, "UTF-8"))
                    .append("&response_type=code")
                    .toString();
        } catch (UnsupportedEncodingException e) {
            log.error("url encoder error : {}", e.toString());
        }

        return url;
    }

    @Override
    public Account getUserModel(String code) {
        KakaoTokenResponse kakaoTokenResponse = this.getTokenFromSocial(code);

        String accessToken = this.getAccessToken(kakaoTokenResponse);
        String url = new StringBuilder(FutConstant.HOST_API)
                .append(FutConstant.API_USER_ME)
                .toString();

        log.info("[ kakao ] token response : {}", kakaoTokenResponse.toString());

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

        if (null == response || null == response.getBody()) {
            log.error("kakao get user : response or response.body is null");
            throw new RestException(ResultCode.NO_NAMING);
        }

        KakaoUserResponse kakaoUserResponse = response.getBody();

        log.info("[ kakao ] user response : {}", kakaoUserResponse.toString());

        return upsertUserModel(kakaoUserResponse, kakaoTokenResponse);
    }

    private Account upsertUserModel(KakaoUserResponse kakaoUserResponse, KakaoTokenResponse kakaoTokenResponse) {
        log.info("[ kakao ] upsert user-model. ");

        String id = null, email = null, nickname = null, imageUrl = null;

        if (!Strings.isNullOrEmpty(kakaoUserResponse.getId())) {
            id = kakaoUserResponse.getId();
        }

        if (kakaoUserResponse.getKakaoAccount() != null && !Strings.isNullOrEmpty(kakaoUserResponse.getKakaoAccount().getEmail())) {
            email = kakaoUserResponse.getKakaoAccount().getEmail();
        }

        if (kakaoUserResponse.getProperties() != null) {
            if (!Strings.isNullOrEmpty(kakaoUserResponse.getProperties().getNickname())) {
                nickname = kakaoUserResponse.getProperties().getNickname();
            }
            if (!Strings.isNullOrEmpty(kakaoUserResponse.getProperties().getThumbnailImage())) {
                imageUrl = kakaoUserResponse.getProperties().getThumbnailImage();
            }
        }

        Account account = accountRepository.findByIdAndEmailAndSocial(id, email, FutConstant.KAKAO);

        //  TODO. upsert user model
        if (null == account) {
            log.info("[ kakao ] find user-model is null.");

            account = new Account();
            account.setId(id);
            account.setEmail(email);
            account.setNickname(nickname);
            account.setImageUrl(imageUrl);
            account.setSocial(FutConstant.KAKAO);
        }

        log.info("[ kakao ] find user-model : {}", account.toString());

        if (!Strings.isNullOrEmpty(kakaoTokenResponse.getRefreshToken())) {
            account.setRefreshToken(kakaoTokenResponse.getRefreshToken());
        }

        account.setAccessToken(kakaoTokenResponse.getAccessToken());

        log.info("[ kakao ] save user-model : {}", account.toString());

        accountRepository.save(account);
        return account;
    }


    @Override
    public KakaoTokenResponse getTokenFromSocial(String code) {
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

    private String getAccessToken(KakaoTokenResponse tokenResponse) {
        return new StringBuilder(tokenResponse.getTokenType())
                .append(" ")
                .append(tokenResponse.getAccessToken())
                .toString();
    }
}
