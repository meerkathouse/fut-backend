package com.meerkat.house.fut.model.team;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.meerkat.house.fut.exception.Code;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamResponse {
    private Integer tid;
    private String name;
    private String emblemUrl;
    private Code code;
}
