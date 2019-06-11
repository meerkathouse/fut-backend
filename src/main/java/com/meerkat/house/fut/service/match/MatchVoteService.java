package com.meerkat.house.fut.service.match;

import com.meerkat.house.fut.model.match_vote.MatchVoteRequest;
import com.meerkat.house.fut.model.match_vote.MatchVoteResponse;

public interface MatchVoteService {
    MatchVoteResponse updateVoteStatus(Integer mid, Integer uid, MatchVoteRequest matchVoteRequest);
    MatchVoteResponse getVoteStatusByMatch(Integer mid);
}
