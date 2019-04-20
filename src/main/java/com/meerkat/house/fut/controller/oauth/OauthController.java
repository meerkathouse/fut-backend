package com.meerkat.house.fut.controller.oauth;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login() {

    }

    @RequestMapping(value = "/oauth/{social}", method = RequestMethod.GET)
    public void oauthRedirectUrl(@PathVariable("social") String social) {

    }
}
