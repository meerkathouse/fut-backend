package com.meerkat.house.fut.service.match;

import com.meerkat.house.fut.model.match_game.MatchGameRequest;
import com.meerkat.house.fut.model.match_game.MatchGameResponse;

public interface MatchGameService {
    MatchGameResponse saveMatchGame(int mid, MatchGameRequest matchGameRequest);
    MatchGameResponse getGamesByMid(int mid);
}
