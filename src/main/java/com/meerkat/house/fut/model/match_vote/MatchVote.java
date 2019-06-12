package com.meerkat.house.fut.model.match_vote;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.meerkat.house.fut.utils.FutConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "match_vote")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchVote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mvid;

    @NotNull
    private Integer mid;

    @NotNull
    private Integer uid;

    private String status = FutConstant.NO_DECISION;
}
