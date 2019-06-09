package com.meerkat.house.fut.service.team;

import com.meerkat.house.fut.exception.RestResponse;
import com.meerkat.house.fut.model.team.Team;
import com.meerkat.house.fut.model.team.TeamRequest;
import com.meerkat.house.fut.model.team.TeamResponse;

import java.util.List;

public interface TeamService {
    TeamResponse upsertTeam(TeamRequest teamRequest);
    List<Team> findAll();
    List<TeamResponse> findAllMine();
    RestResponse deleteTeam(Integer tid);
}
