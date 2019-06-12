package com.meerkat.house.fut.controller.match;

import com.meerkat.house.fut.model.match.Match;
import com.meerkat.house.fut.model.match.MatchRequest;
import com.meerkat.house.fut.model.match.MatchResponse;
import com.meerkat.house.fut.service.match.MatchService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class MatchController {

    @Autowired
    private MatchService matchServiceImpl;

    @RequestMapping(value = "/matches", method = RequestMethod.POST)
    public MatchResponse saveMatch(@RequestBody MatchRequest matchRequest) {
        return matchServiceImpl.saveMatch(matchRequest);
    }

    @RequestMapping(value = "/matches/{mid}/end", method = RequestMethod.PUT)
    public Match endMatch(@PathVariable("mid") Integer mid) {
        return matchServiceImpl.endMatch(mid);
    }

    @RequestMapping(value = "/matches/teams/{tid}", method = RequestMethod.GET)
    public List<Match> getMatchesByTid(@PathVariable("tid") Integer tid,
                                       @RequestParam(value = "year", required = false) Integer year) {
        return matchServiceImpl.getMatchesByTid(tid, year);
    }
}
