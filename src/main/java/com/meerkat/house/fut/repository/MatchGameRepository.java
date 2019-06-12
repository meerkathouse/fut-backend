package com.meerkat.house.fut.repository;

import com.meerkat.house.fut.model.match_game.MatchGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchGameRepository extends JpaRepository<MatchGame, Integer> {
    List<MatchGame> findAll();
    List<MatchGame> findByMid(Integer mid);

    MatchGame findByMidAndMgNum(Integer mid, Integer mgNum);
}
