package com.meerkat.house.fut.service.team;

import com.meerkat.house.fut.model.team_member.TeamMemberResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeamMemberService {
    TeamMemberResponse upsertMember(Integer tid);
    TeamMemberResponse updateMemberStatus(Integer tid, Integer uid);
    TeamMemberResponse rejectMemberStatus(Integer tid, Integer uid);

    List<TeamMemberResponse> findMyStatus(Integer uid);
}
