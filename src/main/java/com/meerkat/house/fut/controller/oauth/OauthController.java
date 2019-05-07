package com.meerkat.house.fut.controller.oauth;

import com.google.common.base.Strings;
import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.RestResponse;
import com.meerkat.house.fut.exception.ResultCode;
import com.meerkat.house.fut.model.oauth.OauthAccountRequest;
import com.meerkat.house.fut.service.social.SocialSelectFactory;
import com.meerkat.house.fut.service.social.oauth.OauthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/oauth")
public class OauthController {

    private OauthService oauthService;

    @Autowired
    private SocialSelectFactory socialSelectFactory;

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public RestResponse saveAccount(@RequestBody @Valid OauthAccountRequest oauthAccountRequest) {

        String social = oauthAccountRequest.getSocial();
        String accessToken = oauthAccountRequest.getAccessToken();

        if(Strings.isNullOrEmpty(social) || Strings.isNullOrEmpty(accessToken)) {
            log.info("social : {}, accessToken : {}", social, accessToken);
            throw new RestException(ResultCode.INFORMATION_INSUFFICIENT);
        }

        oauthService = socialSelectFactory.getSocialOauthService(social);
        return oauthService.upsertAccount(accessToken);
    }
}
