package com.meerkat.house.fut.model.team_member;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tmid;

    private Integer tid;
    private Integer uid;
    private String status;

    @Column(name = "is_creator")
    private Boolean isCreator = false;
}
