package com.meerkat.house.fut.controller.oauth;

import com.meerkat.house.fut.model.UserModel;
import com.meerkat.house.fut.service.social.SocialSelectFactory;
import com.meerkat.house.fut.service.social.oauth.OauthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class OauthController {

    private OauthService oauthService;

    @Autowired
    private SocialSelectFactory socialSelectFactory;

    @RequestMapping(value = "/login/{social}", method = RequestMethod.GET)
    public String login(@PathVariable("social") String social) {
        oauthService = socialSelectFactory.getSocialOauthService(social);
        return "redirect:/" + oauthService.getCodeUrl();
    }

    @RequestMapping(value = "/oauth/codes/{social}", method = RequestMethod.GET)
    public void oauthRedirectUrl(@PathVariable("social") String social,
                                   @RequestParam("code") String code) {
        oauthService = socialSelectFactory.getSocialOauthService(social);
        UserModel userModel = oauthService.getUserModel(code);

        log.info("[ UserModel ] : {}", userModel.toString());
    }
}
