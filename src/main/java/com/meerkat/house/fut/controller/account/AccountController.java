package com.meerkat.house.fut.controller.account;

import com.google.common.base.Strings;
import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.ResultCode;
import com.meerkat.house.fut.model.account.AccountResponse;
import com.meerkat.house.fut.model.account.oauth.OauthAccountRequest;
import com.meerkat.house.fut.service.account.SocialSelectFactory;
import com.meerkat.house.fut.service.account.oauth.OauthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class AccountController {

    private OauthService oauthService;

    @Autowired
    private SocialSelectFactory socialSelectFactory;

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public AccountResponse saveAccount(@RequestBody @Valid OauthAccountRequest oauthAccountRequest) {

        String social = oauthAccountRequest.getSocial();
        String tokenType = oauthAccountRequest.getTokenType();
        String accessToken = oauthAccountRequest.getAccessToken();

        if(Strings.isNullOrEmpty(social) || Strings.isNullOrEmpty(tokenType) || Strings.isNullOrEmpty(accessToken)) {
            log.error("account : {}, tokenType : {}, accessToken : {}", social, tokenType, accessToken);
            throw new RestException(ResultCode.INFORMATION_INSUFFICIENT);
        }

        oauthService = socialSelectFactory.getSocialOauthService(social);
        return oauthService.upsertAccount(tokenType, accessToken);
    }

    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
    public AccountResponse findAccountById(@PathVariable("id") Integer uid) {
        return socialSelectFactory.getSocialOauthService("kakao").findAccountByUid(uid);
    }
}
