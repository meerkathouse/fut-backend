package com.meerkat.house.fut.model.match_game;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "match_game")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchGame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mgid;

    private Integer mid;

    @Column(name = "mg_num")
    private Integer mgNum;

    @Column(name = "home_goals")
    private Integer homeGoals = 0;

    @Column(name = "away_goals")
    private Integer awayGoals = 0;
}
