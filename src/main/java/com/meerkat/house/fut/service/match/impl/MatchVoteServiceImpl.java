package com.meerkat.house.fut.service.match.impl;

import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.ResultCode;
import com.meerkat.house.fut.model.match_vote.MatchVote;
import com.meerkat.house.fut.model.match_vote.MatchVoteRequest;
import com.meerkat.house.fut.model.match_vote.MatchVoteResponse;
import com.meerkat.house.fut.repository.MatchVoteRepository;
import com.meerkat.house.fut.service.match.MatchVoteService;
import com.meerkat.house.fut.utils.FutConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class MatchVoteServiceImpl implements MatchVoteService {

    private final MatchVoteRepository matchVoteRepository;

    @Autowired
    public MatchVoteServiceImpl(MatchVoteRepository matchVoteRepository) {
        this.matchVoteRepository = matchVoteRepository;
    }

    @Override
    public MatchVoteResponse updateVoteStatus(Integer mid, Integer uid, MatchVoteRequest matchVoteRequest) {

        isValidStatus(matchVoteRequest);

        MatchVote matchVote = matchVoteRepository.findByMidAndUid(mid, uid);
        if(null == matchVote) {
            log.error("[MatchVote] Match vote is null");
            throw new RestException(ResultCode.MATCH_VOTE_NOT_FOUND);
        }

        //  TODO. change matchVoteRequest.status to enum class
        matchVote.setStatus(matchVoteRequest.getStatus());
        matchVoteRepository.save(matchVote);

        MatchVoteResponse matchVoteResponse = new MatchVoteResponse();
        matchVoteResponse.setCode(ResultCode.SUCCESS_UPDATE_VOTE_STATUS);
        return matchVoteResponse;
    }

    private void isValidStatus(MatchVoteRequest matchVoteRequest) {
        switch (matchVoteRequest.getStatus()) {
            case FutConstant.ATTEND:
            case FutConstant.NOT_ATTEND:
            case FutConstant.NO_DECISION:
                return;
        }

        log.error("[MatchVote] Invalid status : {}", matchVoteRequest.getStatus());
        throw new RestException(ResultCode.INVALID_VOTE_STATUS);
    }

    @Override
    public MatchVoteResponse getVoteStatusByMatch(Integer mid) {
        List<MatchVote> attendList = new ArrayList<>();
        List<MatchVote> notAttendList = new ArrayList<>();
        List<MatchVote> noDecisionList = new ArrayList<>();

        List<MatchVote> matchVoteList = matchVoteRepository.findByMid(mid);
        for (MatchVote matchVote : matchVoteList) {
            switch (matchVote.getStatus()) {
                case FutConstant.ATTEND:
                    attendList.add(matchVote);
                    break;
                case FutConstant.NOT_ATTEND:
                    notAttendList.add(matchVote);
                    break;
                case FutConstant.NO_DECISION:
                    noDecisionList.add(matchVote);
                    break;
            }
        }

        MatchVoteResponse matchVoteResponse = new MatchVoteResponse();
        matchVoteResponse.setAttendList(attendList);
        matchVoteResponse.setNotAttendList(notAttendList);
        matchVoteResponse.setNoDecisionList(noDecisionList);

        return matchVoteResponse;
    }

}
