package com.meerkat.house.fut.repository;

import com.meerkat.house.fut.model.match.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {
    List<Match> findAll();
}
