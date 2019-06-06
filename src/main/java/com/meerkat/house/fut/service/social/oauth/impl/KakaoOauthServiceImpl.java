package com.meerkat.house.fut.service.social.oauth.impl;

import com.google.common.base.Strings;
import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.ResultCode;
import com.meerkat.house.fut.model.account.Account;
import com.meerkat.house.fut.model.oauth.kakao.KakaoUserProperties;
import com.meerkat.house.fut.model.oauth.kakao.KakaoUserResponse;
import com.meerkat.house.fut.repository.AccountRepository;
import com.meerkat.house.fut.service.social.common.CommonSocialOauthService;
import com.meerkat.house.fut.service.social.oauth.OauthService;
import com.meerkat.house.fut.utils.FutConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class KakaoOauthServiceImpl implements OauthService {

    @Value("${socials.kakao.api.host}")
    private String apiHost;

    @Value("${socials.kakao.api.user}")
    private String apiUser;

    private RestTemplate restTemplate;
    private AccountRepository accountRepository;
    private CommonSocialOauthService commonSocialOauthService;

    @Autowired
    public KakaoOauthServiceImpl(RestTemplate restTemplate, AccountRepository accountRepository, CommonSocialOauthService commonSocialOauthService) {
        this.restTemplate = restTemplate;
        this.accountRepository = accountRepository;
        this.commonSocialOauthService = commonSocialOauthService;
    }

    public Account upsertAccount(String tokenType, String accessToken) {

        String token = commonSocialOauthService.setSocialToken(tokenType, accessToken);

        KakaoUserResponse kakaoUserResponse = callKakaoOauth(token);

        Account account = createAccount(kakaoUserResponse);

        accountRepository.save(account);

        //  TODO.
        //  create token
        //  set session

        return account;
    }

    private KakaoUserResponse callKakaoOauth(String token) {
        HttpHeaders headers = setHeaders(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = new StringBuilder(apiHost)
                .append(apiUser)
                .toString();

        ResponseEntity<KakaoUserResponse> response = null;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, KakaoUserResponse.class);
        } catch (HttpClientErrorException | ResourceAccessException e) {
            log.error("[KAKAO] Call social server for user info. Cause : {}", e.toString());
            throw new RestException(ResultCode.KAKAO_HTTP_ERROR);
        }

        isValidResponse(response);
        return response.getBody();
    }

    private HttpHeaders setHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(HttpHeaders.AUTHORIZATION, token);

        return headers;
    }

    private void isValidResponse(ResponseEntity<KakaoUserResponse> response) {
        if(null == response || null == response.getBody()) {
            log.error("kakao response not found.");
            throw new RestException(ResultCode.KAKAO_RESPONSE_NOT_FOUND);
        }
    }

    private Account createAccount(KakaoUserResponse response) {
        Optional<KakaoUserResponse> optResponse = Optional.ofNullable(response);

        String id = optResponse.map(KakaoUserResponse::getId)
                .orElse(null);

        String email = null;

        String nickName = optResponse.map(KakaoUserResponse::getProperties)
                .map(KakaoUserProperties::getNickname)
                .orElse(null);

        String imageUrl = optResponse.map(KakaoUserResponse::getProperties)
                .map(KakaoUserProperties::getThumbnailImage)
                .orElse(null);

        String social = FutConstant.KAKAO;

        if(Strings.isNullOrEmpty(id)) {
            log.error("[KAKAO] Create account. Id is null.");
            throw new RestException();
        }

        Account account = accountRepository.findByIdAndEmailAndSocial(id, email, social);
        if(null == account) {
            account = new Account();
            account.setId(id);
            account.setEmail(email);
            account.setSocial(social);
        }

        account.setNickname(nickName);
        account.setImageUrl(imageUrl);

        return account;
    }
}
