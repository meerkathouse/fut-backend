package com.meerkat.house.fut.service.match;

import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.ResultCode;
import com.meerkat.house.fut.model.match.MatchRequest;
import com.meerkat.house.fut.model.match.MatchResponse;
import com.meerkat.house.fut.repository.MatchRepository;
import com.meerkat.house.fut.repository.TeamMemberRepository;
import com.meerkat.house.fut.utils.CurrentInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MatchServiceImpl {

    private final MatchRepository matchRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Autowired
    MatchServiceImpl(MatchRepository matchRepository, TeamMemberRepository teamMemberRepository) {
        this.matchRepository = matchRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    public MatchResponse upsertMatch(MatchRequest matchRequest) {
        Integer uid = CurrentInfoUtils.getUid();
        if(null == uid) {
            log.error("[MATCH] Uid not found");
            throw new RestException(ResultCode.UID_NOT_FOUND);
        }

        return null;
    }
}
