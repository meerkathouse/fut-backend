package com.meerkat.house.fut.service.team.impl;

import com.google.common.base.Strings;
import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.RestResponse;
import com.meerkat.house.fut.exception.ResultCode;
import com.meerkat.house.fut.model.team.Team;
import com.meerkat.house.fut.model.team.TeamRequest;
import com.meerkat.house.fut.model.team.TeamResponse;
import com.meerkat.house.fut.model.team_member.TeamMember;
import com.meerkat.house.fut.repository.TeamMemberRepository;
import com.meerkat.house.fut.repository.TeamRepository;
import com.meerkat.house.fut.service.team.TeamService;
import com.meerkat.house.fut.utils.CurrentInfoUtils;
import com.meerkat.house.fut.utils.FutConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, TeamMemberRepository teamMemberRepository) {
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    @Override
    public TeamResponse upsertTeam(TeamRequest teamRequest) {
        Integer uid = CurrentInfoUtils.getUid();
        if (null == uid) {
            log.error("[TEAM] Uid not found");
            throw new RestException(ResultCode.UID_NOT_FOUND);
        }

        Team team = createTeam(uid, teamRequest);
        teamRepository.save(team);

        saveTeamMember(uid, team.getTid());

        return TeamResponse.builder()
                .tid(team.getTid())
                .code(ResultCode.SUCCESS_TEAM_CREATE)
                .build();
    }

    @Override
    public List<Team> findAll() {
        List<Team> teamList = teamRepository.findAll();
        if (teamList.isEmpty()) {
            log.error("[Team] Find all. Team is empty");
            throw new RestException(ResultCode.TEAM_NOT_FOUND);
        }

        return teamList;
    }

    @Override
    public List<TeamResponse> findAllMine() {
        Integer uid = CurrentInfoUtils.getUid();
        if (null == uid) {
            log.error("[TEAM] Uid not found");
            throw new RestException(ResultCode.UID_NOT_FOUND);
        }

        List<TeamResponse> teamList = new ArrayList<>();

        List<TeamMember> teamMemberList = teamMemberRepository.findAllByStatusAndUid(FutConstant.APPROVED, uid);
        for (TeamMember teamMember : teamMemberList) {
            Team team = teamRepository.findByTid(teamMember.getTid());
            TeamResponse teamResponse = TeamResponse.builder()
                    .tid(team.getTid())
                    .name(team.getName())
                    .emblemUrl(team.getEmblemUrl())
                    .build();
            teamList.add(teamResponse);
        }

        return teamList;
    }

    @Override
    public RestResponse deleteTeam(Integer tid) {
        if (null == tid) {
            log.error("[Team] Delete team. Tid is null");
            throw new RestException(ResultCode.TID_IS_NULL);
        }

        Team team = teamRepository.findByTid(tid);
        if (null == team) {
            log.error("[Team] Delete team. Team is null");
            throw new RestException(ResultCode.TEAM_NOT_FOUND);
        }

        Integer uid = CurrentInfoUtils.getUid();
        if(null == uid) {
            log.error("[Team] Delete team. Uid not found");
            throw new RestException(ResultCode.UID_NOT_FOUND);
        }

        if(team.getUid() != uid ) {
            log.error("[Team] Delete team. Invalid uid");
            throw new RestException(ResultCode.INVALID_UID);
        }

        List<TeamMember> teamMemberList = teamMemberRepository.findAllByTid(team.getTid());
        teamMemberRepository.deleteAll(teamMemberList);

        teamRepository.delete(team);

        return new RestResponse(ResultCode.SUCCESS_TEAM_DELETE);
    }

    private Team createTeam(Integer uid, TeamRequest teamRequest) {
        Optional<TeamRequest> optRequest = Optional.ofNullable(teamRequest);

        String name = optRequest.map(TeamRequest::getName)
                .orElse(null);

        String si = optRequest.map(TeamRequest::getSi)
                .orElse(null);

        String gun = optRequest.map(TeamRequest::getGun)
                .orElse(null);

        String gu = optRequest.map(TeamRequest::getGu)
                .orElse(null);

        if (Strings.isNullOrEmpty(name)) {
            throw new RestException(ResultCode.TEAM_NAME_IS_NULL);
        }

        Team team = teamRepository.findByNameAndUid(name, uid);
        if (team != null) {
            throw new RestException(ResultCode.TEAM_ALREADY_EXIST);
        }

        team = new Team();
        team.setName(name);
        team.setUid(uid);
        team.setSi(si);
        team.setGun(gun);
        team.setGu(gu);

        return team;
    }

    private void saveTeamMember(Integer uid, Integer tid) {
        try {
            TeamMember teamMember = TeamMember.builder()
                    .tid(tid)
                    .uid(uid)
                    .status(FutConstant.APPROVED)
                    .isCreator(true)
                    .build();

            teamMemberRepository.save(teamMember);
        } catch (Exception e) {
            log.error("[TEAM] Create team. Save team member error. Cause : {}", e.toString());
            throw new RestException();
        }
    }
}
