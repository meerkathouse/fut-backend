package com.meerkat.house.fut.service.social.oauth.impl;

import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.RestResponse;
import com.meerkat.house.fut.exception.ResultCode;
import com.meerkat.house.fut.model.Account;
import com.meerkat.house.fut.model.oauth.kakao.KakaoUserResponse;
import com.meerkat.house.fut.repository.AccountRepository;
import com.meerkat.house.fut.service.social.oauth.OauthService;
import com.meerkat.house.fut.utils.FutConstant;
import com.sun.corba.se.impl.oa.toa.TOA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Transactional
public class KakaoOauthServiceImpl implements OauthService {

    private RestTemplate restTemplate;
    private AccountRepository accountRepository;

    @Autowired
    public KakaoOauthServiceImpl(RestTemplate restTemplate, AccountRepository accountRepository) {
        this.restTemplate = restTemplate;
        this.accountRepository = accountRepository;
    }

    public RestResponse upsertAccount(String tokenType, String accessToken) {

        String token = setToken(tokenType, accessToken);

        HttpHeaders headers = getHeaders(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = getUrl(FutConstant.HOST_API, FutConstant.API_USER_ME);

        ResponseEntity<KakaoUserResponse> response = null;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, KakaoUserResponse.class);
        } catch (HttpClientErrorException | ResourceAccessException e) {
            log.error("kakao get user : http client error. cause : {}", e.toString());
            throw new RestException(ResultCode.KAKAO_HTTP_ERROR);
        }

        KakaoUserResponse kakaoUserResponse = getKakaoResponse(response);
        Account account = createAccount(kakaoUserResponse);

        accountRepository.save(account);

        return new RestResponse(ResultCode.SUCCESS);
    }

    private String getUrl(String host, String api) {
        String url = new StringBuilder(host)
                .append(api)
                .toString();

        return url;
    }

    private HttpHeaders getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", token);

        return headers;
    }

    private String setToken(String type, String token) {
        return new StringBuilder(type)
                .append(" ")
                .append(token)
                .toString();
    }

    private KakaoUserResponse getKakaoResponse(ResponseEntity<KakaoUserResponse> response) {
        isValidResponse(response);
        return response.getBody();
    }

    private void isValidResponse(ResponseEntity<KakaoUserResponse> response) {
        if(null == response || null == response.getBody()) {
            log.error("kakao response not found.");
            throw new RestException(ResultCode.KAKAO_RESPONSE_NOT_FOUND);
        }
    }

    private Account createAccount(KakaoUserResponse response) {
        Account account = new Account();
        account.setId(response.getId());
        account.setNickname(response.getProperties().getNickname());
        account.setImageUrl(response.getProperties().getThumbnailImage());
        account.setSocial(FutConstant.KAKAO);

        return account;
    }
}
