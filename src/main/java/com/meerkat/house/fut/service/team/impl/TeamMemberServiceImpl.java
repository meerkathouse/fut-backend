package com.meerkat.house.fut.service.team.impl;

import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.ResultCode;
import com.meerkat.house.fut.model.account.Account;
import com.meerkat.house.fut.model.team.Team;
import com.meerkat.house.fut.model.team_member.TeamMember;
import com.meerkat.house.fut.model.team_member.TeamMemberResponse;
import com.meerkat.house.fut.repository.AccountRepository;
import com.meerkat.house.fut.repository.TeamMemberRepository;
import com.meerkat.house.fut.repository.TeamRepository;
import com.meerkat.house.fut.service.team.TeamMemberService;
import com.meerkat.house.fut.utils.CurrentInfoUtils;
import com.meerkat.house.fut.utils.FutConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    private final AccountRepository accountRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Autowired
    TeamMemberServiceImpl(AccountRepository accountRepository, TeamRepository teamRepository, TeamMemberRepository teamMemberRepository) {
        this.accountRepository = accountRepository;
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    @Override
    public TeamMemberResponse upsertMember(Integer tid) {
        Integer uid = CurrentInfoUtils.getUid();
        if(null == uid) {
            log.error("[TEAM_MEMBER] Uid is null");
            throw new RestException(ResultCode.UID_NOT_FOUND);
        }

        Team team = findTeamByTid(tid);

        TeamMember teamMember = createTeamMember(tid, uid);
        teamMemberRepository.save(teamMember);

        return setTeamMemberResponse(teamMember, team);
    }

    @Override
    public TeamMemberResponse updateMemberStatus(Integer tid, Integer uid) {
        if(null == tid || null == uid) {
            log.error("[TEAM_MEMBER] Request is invalid");
            throw new RestException();
        }

        Team team = findTeamByTid(tid);

        TeamMember teamMember = setApprovalStatus(tid, uid, FutConstant.APPROVED);
        teamMemberRepository.save(teamMember);

        return setTeamMemberResponse(teamMember, team);
    }

    @Override
    public TeamMemberResponse rejectMemberStatus(Integer tid, Integer uid) {
        if(null == tid || null == uid) {
            log.error("[TEAM_MEMBER] Request is invalid");
            throw new RestException();
        }

        Team team = findTeamByTid(tid);

        //  TODO. update status or delete entity
        TeamMember teamMember = setApprovalStatus(tid, uid, FutConstant.REJECT);
        teamMemberRepository.save(teamMember);

        return setTeamMemberResponse(teamMember, team);
    }

    @Override
    public List<TeamMemberResponse> findMyStatus(Integer uid) {
        if(null == uid) {
            log.error("[TEAM_MEMBER] Uid not found");
            throw new RestException(ResultCode.UID_NOT_FOUND);
        }

        Account account = accountRepository.findByUid(uid);
        if( null == account) {
            log.error("[TeamMember] Account not found");
            throw new RestException(ResultCode.ACCOUNT_NOT_FOUND);
        }

        List<TeamMember> teamMemberList = teamMemberRepository.findAllByUid(uid);
        return setTMResponseList(teamMemberList);
    }

    private TeamMember setApprovalStatus(Integer tid, Integer uid, String status) {
        TeamMember teamMember = teamMemberRepository.findByTidAndUid(tid, uid);
        if(null == teamMember) {
            log.error("[TEAM_MEMBER] Approval request not found");
            throw new RestException(ResultCode.APPROVAL_REQUEST_NOT_FOUND);
        }

        if(teamMember.getStatus().equalsIgnoreCase(FutConstant.APPROVED)) {
            log.error("[TeamMember] Already approved.");
            throw new RestException(ResultCode.ALREADY_APPROVED);
        }

        teamMember.setStatus(status);
        return teamMember;
    }

    private TeamMember createTeamMember(Integer tid, Integer uid) {
        TeamMember teamMember = teamMemberRepository.findByTidAndUid(tid, uid);
        if(null == teamMember) {
            //  TODO. change status to enum class
            teamMember = new TeamMember();
            teamMember.setTid(tid);
            teamMember.setUid(uid);
            teamMember.setStatus(FutConstant.APPROVING);
        }
        return teamMember;
    }

    private Team findTeamByTid(Integer tid) {
        Team team = teamRepository.findByTid(tid);
        if(null == team) {
            log.error("[TEAM_MEMBER] Team not found");
            throw new RestException(ResultCode.TEAM_NOT_FOUND);
        }
        return team;
    }

    private TeamMemberResponse setTeamMemberResponse(TeamMember teamMember, Team team) {
        return TeamMemberResponse.builder()
                .team(team.getName())
                .status(teamMember.getStatus())
                .build();
    }

    private List<TeamMemberResponse> setTMResponseList(List<TeamMember> teamMemberList) {
        List<TeamMemberResponse> tmlResponseList = new ArrayList<>();
        for (TeamMember teamMember : teamMemberList) {
            Team team = teamRepository.findByTid(teamMember.getTid());
            TeamMemberResponse teamMemberResponse = TeamMemberResponse.builder()
                    .team(team.getName())
                    .status(teamMember.getStatus())
                    .build();
            tmlResponseList.add(teamMemberResponse);
        }
        return tmlResponseList;
    }
}
