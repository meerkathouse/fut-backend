package com.meerkat.house.fut.model.team;

import com.meerkat.house.fut.exception.Code;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {
    private Integer tid;
    private Code code;
}
