package com.meerkat.house.fut.model.match_game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchGameRequest implements Serializable {

    private Integer mgid;

    @NotNull
    private Integer mgNum;

    @NotNull
    private Integer homeGoals;

    @NotNull
    private Integer awayGoals;
}
