package com.meerkat.house.fut.model.match;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchRequest implements Serializable {

    @NotNull
    private Integer homeTid;
    private String homeName;

    @NotNull
    private String awayName;
    private String stadiumName;
    private Long stadiumLatitude;
    private Long stadiumLongitude;
    private String status;
}
