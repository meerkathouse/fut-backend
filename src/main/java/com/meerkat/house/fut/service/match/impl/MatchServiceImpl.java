package com.meerkat.house.fut.service.match.impl;

import com.google.common.base.Strings;
import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.ResultCode;
import com.meerkat.house.fut.model.match.Match;
import com.meerkat.house.fut.model.match.MatchRequest;
import com.meerkat.house.fut.model.match.MatchResponse;
import com.meerkat.house.fut.model.match_vote.MatchVote;
import com.meerkat.house.fut.model.team.Team;
import com.meerkat.house.fut.model.team_member.TeamMember;
import com.meerkat.house.fut.repository.MatchRepository;
import com.meerkat.house.fut.repository.MatchVoteRepository;
import com.meerkat.house.fut.repository.TeamMemberRepository;
import com.meerkat.house.fut.repository.TeamRepository;
import com.meerkat.house.fut.service.match.MatchService;
import com.meerkat.house.fut.utils.CurrentInfoUtils;
import com.meerkat.house.fut.utils.FutConstant;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final MatchVoteRepository matchVoteRepository;

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Autowired
    MatchServiceImpl(MatchRepository matchRepository, MatchVoteRepository matchVoteRepository, TeamRepository teamRepository, TeamMemberRepository teamMemberRepository) {
        this.matchRepository = matchRepository;
        this.matchVoteRepository = matchVoteRepository;
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    @Override
    public MatchResponse saveMatch(MatchRequest matchRequest) {
        String homeName = getHomeName(matchRequest);

        isValidTeamMember(matchRequest);

        Match match = createMatch(homeName, matchRequest);
        matchRepository.save(match);

        List<MatchVote> matchVoteList = createVotes(match);
        sendPushVotes(matchVoteList);

        return MatchResponse.builder()
                .mid(match.getMid())
                .homeName(match.getHomeName())
                .awayName(match.getAwayName())
                .build();
    }

    @Override
    public Match endMatch(int mid) {
        Match match = matchRepository.findByMid(mid);
        if (null == match) {
            log.error("[Match] Match not found");
            throw new RestException(ResultCode.MATCH_NOT_FOUND);
        }

        updateEndMatch(match);
        return match;
    }

    @Override
    public List<Match> getMatchesByTid(Integer tid, Integer year) {
        Team team = teamRepository.findByTid(tid);
        if (null == team) {
            log.error("[Match] Find matches by tid.");
            throw new RestException(ResultCode.TEAM_NOT_FOUND);
        }

        //  TODO. paging by year
        List<Match> matchList = matchRepository.findByHomeTid(tid);
        return matchList;
    }

    private String getHomeName(MatchRequest matchRequest) {
        String homeName = matchRequest.getHomeName();

        if (Strings.isNullOrEmpty(homeName)) {
            Team team = teamRepository.findByTid(matchRequest.getHomeTid());
            if (null == team) {
                log.error("[Match] Create match. Team not found");
                throw new RestException(ResultCode.TEAM_NOT_FOUND);
            }
            homeName = team.getName();
        }
        return homeName;
    }

    private void isValidTeamMember(MatchRequest matchRequest) {
        int uid = CurrentInfoUtils.getUid();
        int tid = matchRequest.getHomeTid();

        TeamMember teamMember = teamMemberRepository.findByTidAndUid(tid, uid);
        if (null == teamMember) {
            log.error("[Match] Create match. User is not a member.");
            throw new RestException(ResultCode.USER_NOT_A_MEMBER);
        }
    }

    private Match createMatch(String homeName, MatchRequest matchRequest) {
        Match match = Match.builder()
                .homeTid(matchRequest.getHomeTid())
                .homeName(homeName)
                .awayName(matchRequest.getAwayName())
                .matchDate(System.currentTimeMillis())
                .stadiumName(matchRequest.getStadiumName())
                .stadiumLatitude(matchRequest.getStadiumLatitude())
                .stadiumLongitude(matchRequest.getStadiumLongitude())
                .homeGoals(0)
                .awayGoals(0)
                .status(FutConstant.BEFORE)
                .winner(FutConstant.DRAW)
                .build();
        return match;
    }

    private List<MatchVote> createVotes(Match match) {
        int mid = match.getMid();
        int tid = match.getHomeTid();

        List<TeamMember> teamMemberList = teamMemberRepository.findAllByTid(tid);
        List<MatchVote> matchVoteList = new ArrayList<>();

        for (TeamMember teamMember : teamMemberList) {
            MatchVote matchVote = new MatchVote();
            matchVote.setMid(mid);
            matchVote.setUid(teamMember.getUid());
            matchVoteList.add(matchVote);
            matchVoteRepository.save(matchVote);
        }

        return matchVoteList;
    }

    private void updateEndMatch(Match match) {
        int homeGoals = match.getHomeGoals();
        int awayGoals = match.getAwayGoals();
        int winner = homeGoals > awayGoals ? FutConstant.HOME_WIN : homeGoals == awayGoals ? FutConstant.DRAW : FutConstant.AWAY_WIN;

        match.setWinner(winner);
        match.setStatus(FutConstant.END);
        matchRepository.save(match);
    }

    private void sendPushVotes(List<MatchVote> matchVoteList) {
        //  TODO. send push for vote
    }
}
