package com.meerkat.house.fut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/views")
public class ViewController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String viewLogin() {
        return "login";
    }
}
