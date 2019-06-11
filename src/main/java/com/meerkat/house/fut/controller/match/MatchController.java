package com.meerkat.house.fut.controller.match;

import com.meerkat.house.fut.model.match.MatchRequest;
import com.meerkat.house.fut.model.match.MatchResponse;
import com.meerkat.house.fut.service.match.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MatchController {

    @Autowired
    private MatchService matchServiceImpl;

    @RequestMapping(value = "/matches", method = RequestMethod.POST)
    public MatchResponse saveMatch(@RequestBody MatchRequest matchRequest) {
        return matchServiceImpl.saveMatch(matchRequest);
    }
}
