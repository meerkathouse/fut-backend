package com.meerkat.house.fut.controller.match;

import com.meerkat.house.fut.model.match.MatchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MatchController {

    @RequestMapping(value = "/match", method = RequestMethod.POST)
    public void upsertMatch(@RequestBody MatchRequest matchRequest) {

    }
}
