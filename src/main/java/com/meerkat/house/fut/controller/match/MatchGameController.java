package com.meerkat.house.fut.controller.match;

import com.meerkat.house.fut.model.match_game.MatchGameRequest;
import com.meerkat.house.fut.model.match_game.MatchGameResponse;
import com.meerkat.house.fut.service.match.MatchGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class MatchGameController {

    @Autowired
    private MatchGameService matchGameService;

    @RequestMapping(value = "/matches/{mid}/games", method = RequestMethod.POST)
    public MatchGameResponse saveMatchGame(@PathVariable("mid") Integer mid,
                                           @Valid @RequestBody MatchGameRequest matchGameRequest) {
        return matchGameService.saveMatchGame(mid, matchGameRequest);
    }

    @RequestMapping(value = "/matches/{mid}/games", method = RequestMethod.GET)
    public MatchGameResponse getGamesByMid(@PathVariable("mid") Integer mid) {
        return matchGameService.getGamesByMid(mid);
    }
}
