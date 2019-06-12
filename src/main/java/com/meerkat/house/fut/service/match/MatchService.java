package com.meerkat.house.fut.service.match;


import com.meerkat.house.fut.model.match.Match;
import com.meerkat.house.fut.model.match.MatchRequest;
import com.meerkat.house.fut.model.match.MatchResponse;

import java.util.List;

public interface MatchService {
    MatchResponse saveMatch(MatchRequest matchRequest);
    Match endMatch(int mid);

    List<Match> getMatchesByTid(Integer tid, Integer year);
}
