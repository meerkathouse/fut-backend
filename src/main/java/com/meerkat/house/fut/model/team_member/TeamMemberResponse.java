package com.meerkat.house.fut.model.team_member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberResponse implements Serializable {
    private String team;
    private String status;
}
