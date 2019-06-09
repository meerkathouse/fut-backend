package com.meerkat.house.fut.controller.team;

import com.meerkat.house.fut.exception.RestResponse;
import com.meerkat.house.fut.model.team.Team;
import com.meerkat.house.fut.model.team.TeamRequest;
import com.meerkat.house.fut.model.team.TeamResponse;
import com.meerkat.house.fut.service.team.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @RequestMapping(value = "/team", method = RequestMethod.POST)
    public TeamResponse createTeam(@Valid @RequestBody TeamRequest teamRequest) {
        return teamService.upsertTeam(teamRequest);
    }

    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public List<Team> findAllTeams() {
        return teamService.findAll();
    }

    @RequestMapping(value = "/teams/mine", method = RequestMethod.GET)
    public List<Team> findByMy() {
        return teamService.findByUid();
    }

    @RequestMapping(value = "/teams/{tid}", method = RequestMethod.DELETE)
    public RestResponse deleteTeam(@PathVariable("tid") Integer tid) {
        return teamService.deleteTeam(tid);
    }
}
