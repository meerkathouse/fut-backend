package com.meerkat.house.fut.model.team;

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
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tid;

    private Integer uid;
    private String name;
    private String si;
    private String gun;
    private String gu;

    @Column(name = "emblem_url")
    private String emblemUrl;
}
