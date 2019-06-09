package com.meerkat.house.fut.controller.team;

import com.meerkat.house.fut.model.team_member.TeamMember;
import com.meerkat.house.fut.model.team_member.TeamMemberResponse;
import com.meerkat.house.fut.service.team.TeamMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @RequestMapping(value = "/teams/{tid}/member", method = RequestMethod.POST)
    public TeamMemberResponse saveMember(@PathVariable("tid") Integer tid) {
        return teamMemberService.upsertMember(tid);
    }

    @RequestMapping(value = "/teams/{tid}/members/{uid}", method = RequestMethod.PUT)
    public TeamMemberResponse updateMemberStatus(@PathVariable("tid") Integer tid,
                                                 @PathVariable("uid") Integer uid) {
        return teamMemberService.updateMemberStatus(tid, uid);
    }

    @RequestMapping(value = "/teams/{tid}/members/{uid}", method = RequestMethod.DELETE)
    public TeamMemberResponse rejectApprove(@PathVariable("tid") Integer tid,
                                   @PathVariable("uid") Integer uid) {
        return teamMemberService.rejectMemberStatus(tid, uid);
    }

    @RequestMapping(value = "/teams/members/{uid}", method = RequestMethod.GET)
    public List<TeamMemberResponse> findMyStatus(@PathVariable("uid") Integer uid) {
        return teamMemberService.findMyStatus(uid);
    }
}
