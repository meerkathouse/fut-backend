package com.meerkat.house.fut.service.match.impl;

import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.ResultCode;
import com.meerkat.house.fut.model.match.Match;
import com.meerkat.house.fut.model.match_game.MatchGame;
import com.meerkat.house.fut.model.match_game.MatchGameRequest;
import com.meerkat.house.fut.model.match_game.MatchGameResponse;
import com.meerkat.house.fut.repository.MatchGameRepository;
import com.meerkat.house.fut.repository.MatchRepository;
import com.meerkat.house.fut.service.match.MatchGameService;
import com.meerkat.house.fut.utils.FutConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class MatchGameServiceImpl implements MatchGameService {

    private final MatchRepository matchRepository;
    private final MatchGameRepository matchGameRepository;

    @Autowired
    public MatchGameServiceImpl(MatchRepository matchRepository, MatchGameRepository matchGameRepository) {
        this.matchRepository = matchRepository;
        this.matchGameRepository = matchGameRepository;
    }

    @Override
    public MatchGameResponse saveMatchGame(int mid, MatchGameRequest matchGameRequest) {

        Match match = isValidMid(mid);
        updateMatchGoals(match, matchGameRequest);

        MatchGame matchGame = createMatchGame(mid, matchGameRequest);
        matchGameRepository.save(matchGame);

        MatchGameResponse matchGameResponse = new MatchGameResponse();
        matchGameResponse.setMatchInfo(match);
        matchGameResponse.setMatchGame(matchGame);
        matchGameResponse.setCode(ResultCode.SUCCESS_MATCH_GAME_CREATE);

        return matchGameResponse;
    }

    @Override
    public MatchGameResponse getGamesByMid(int mid) {

        Match match = isValidMid(mid);

        List<MatchGame> matchGameList = matchGameRepository.findByMid(mid);
        if(matchGameList.isEmpty()) {
            log.error("[MatchGame] Match game not found");
            throw new RestException(ResultCode.MATCH_GAME_NOT_FOUND);
        }

        MatchGameResponse matchGameResponse = new MatchGameResponse();
        matchGameResponse.setMatchInfo(match);
        matchGameResponse.setMatchGameList(matchGameList);

        return matchGameResponse;
    }

    private MatchGame createMatchGame(int mid, MatchGameRequest matchGameRequest) {

        Integer mgNum = matchGameRequest.getMgNum();
        if (null == mgNum) {
            log.error("[MatchGame] Request mgNum is null");
            throw new RestException();
        }

        MatchGame matchGame = matchGameRepository.findByMidAndMgNum(mid, mgNum);
        if(matchGame != null) {
            log.error("[MatchGame] Already exist match game");
            throw new RestException(ResultCode.ALREADY_EXIST_MATCH_GAME);
        }

        matchGame = new MatchGame();
        matchGame.setMid(mid);
        matchGame.setMgNum(mgNum);
        matchGame.setHomeGoals(matchGameRequest.getHomeGoals());
        matchGame.setAwayGoals(matchGameRequest.getAwayGoals());

        return matchGame;
    }

    private Match isValidMid(int mid) {
        Match match = matchRepository.findByMid(mid);
        if (null == match) {
            log.error("[MatchGame] Invalid mid. Match not found");
            throw new RestException(ResultCode.MATCH_NOT_FOUND);
        }

        return match;
    }

    private void updateMatchGoals(Match match, MatchGameRequest matchGameRequest) {
        int homeGoals = match.getHomeGoals();
        int awayGoals = match.getAwayGoals();

        match.setHomeGoals(homeGoals + matchGameRequest.getHomeGoals());
        match.setAwayGoals(awayGoals + matchGameRequest.getAwayGoals());
        match.setStatus(FutConstant.PLAYING);

        matchRepository.save(match);
    }
}
