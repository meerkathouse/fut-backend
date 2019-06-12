package com.meerkat.house.fut.model.match;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fut_match")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mid;

    @Column(name = "home_tid")
    private Integer homeTid;

    @Column(name = "home_name")
    private String homeName;

    @Column(name = "away_name")
    private String awayName;

    @Column(name = "stm_name")
    private String stadiumName;

    @Column(name = "stm_lat")
    private Long stadiumLatitude;

    @Column(name = "stm_lon")
    private Long stadiumLongitude;

    @Column(name = "home_goals")
    private Integer homeGoals;

    @Column(name = "away_goals")
    private Integer awayGoals;

    @Column(name = "winner")
    private Integer winner;

    @Column(name = "status")
    private String status;

    @Column(name = "match_date")
    private Long matchDate;
}
