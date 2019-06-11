package com.meerkat.house.fut.service.match;


import com.meerkat.house.fut.model.match.MatchRequest;
import com.meerkat.house.fut.model.match.MatchResponse;

public interface MatchService {
    MatchResponse saveMatch(MatchRequest matchRequest);
}
