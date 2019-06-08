package com.meerkat.house.fut.repository;

import com.meerkat.house.fut.model.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

    List<Team> findAll();
    List<Team> findByUid(Integer uid);

    Team findByTid(Integer tid);
    Team findByNameAndUid(String name, Integer uid);
}
