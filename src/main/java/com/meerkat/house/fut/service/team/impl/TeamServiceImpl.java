package com.meerkat.house.fut.service.team.impl;

import com.google.common.base.Strings;
import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.RestResponse;
import com.meerkat.house.fut.exception.ResultCode;
import com.meerkat.house.fut.model.team.Team;
import com.meerkat.house.fut.model.team.TeamRequest;
import com.meerkat.house.fut.model.team.TeamResponse;
import com.meerkat.house.fut.repository.TeamRepository;
import com.meerkat.house.fut.service.team.TeamService;
import com.meerkat.house.fut.utils.CurrentInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public TeamResponse upsertTeam(TeamRequest teamRequest) {
        Team team = createTeam(teamRequest);
        teamRepository.save(team);

        return new TeamResponse(team.getTid(), ResultCode.SUCCESS_TEAM_CREATE);
    }

    @Override
    public List<Team> findAll() {
        List<Team> teamList = teamRepository.findAll();
        if(teamList.isEmpty()) {
            log.error("[Team] Find all. Team is empty");
            throw new RestException(ResultCode.TEAM_NOT_FOUND);
        }

        return teamList;
    }

    @Override
    public List<Team> findByUid() {
        Integer uid = CurrentInfoUtils.getUid();
        if (null == uid) {
            log.error("[Team] Find by uid. Uid is null");
            throw new RestException();
        }

        List<Team> teamList = teamRepository.findByUid(uid);
        if(teamList.isEmpty()) {
            log.error("[Team] Find by uid. Team is empty");
            throw new RestException(ResultCode.TEAM_NOT_FOUND);
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
        if (team.getUid() != uid) {
            log.error("[Team] Delete team. Invalid uid");
            throw new RestException(ResultCode.INVALID_UID);
        }

        teamRepository.delete(team);

        return new RestResponse(ResultCode.SUCCESS_TEAM_DELETE);
    }

    private Team createTeam(TeamRequest teamRequest) {
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

        Integer uid = CurrentInfoUtils.getUid();
        if (null == uid) {
            throw new RestException();
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
}
