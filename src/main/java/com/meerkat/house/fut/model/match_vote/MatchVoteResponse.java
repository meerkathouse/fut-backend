package com.meerkat.house.fut.model.match_vote;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.meerkat.house.fut.exception.Code;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchVoteResponse implements Serializable {
    private Code code;

    List<MatchVote> attendList;
    List<MatchVote> notAttendList;
    List<MatchVote> noDecisionList;
}
