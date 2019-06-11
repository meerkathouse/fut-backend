package com.meerkat.house.fut.controller.match;

import com.meerkat.house.fut.model.match_vote.MatchVoteRequest;
import com.meerkat.house.fut.model.match_vote.MatchVoteResponse;
import com.meerkat.house.fut.service.match.MatchVoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class MatchVoteController {

    @Autowired
    private MatchVoteService matchVoteServiceImpl;

    @RequestMapping(value = "/matches/{mid}/members/{uid}", method = RequestMethod.PUT)
    public MatchVoteResponse updateVoteStatus(@PathVariable("mid") Integer mid,
                                              @PathVariable("uid") Integer uid,
                                              @Valid @RequestBody MatchVoteRequest matchVoteRequest) {
        return matchVoteServiceImpl.updateVoteStatus(mid, uid, matchVoteRequest);
    }

    @RequestMapping(value = "/matches/{mid}/votes", method = RequestMethod.GET)
    public MatchVoteResponse getVoteStatusByMatch(@PathVariable("mid") Integer mid) {
        return matchVoteServiceImpl.getVoteStatusByMatch(mid);
    }
}
