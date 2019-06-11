package com.meerkat.house.fut.model.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponse implements Serializable {
    private Integer mid;
    private String homeName;
    private String awayName;
}
