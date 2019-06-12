package com.meerkat.house.fut.model.match_game;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.meerkat.house.fut.exception.Code;
import com.meerkat.house.fut.model.match.Match;
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
public class MatchGameResponse implements Serializable {
    private Code code;
    private Match matchInfo;
    private MatchGame matchGame;
    private List<MatchGame> matchGameList;
}
