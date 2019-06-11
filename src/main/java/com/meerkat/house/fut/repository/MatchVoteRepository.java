package com.meerkat.house.fut.repository;

import com.meerkat.house.fut.model.match_vote.MatchVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchVoteRepository extends JpaRepository<MatchVote, Integer> {
    List<MatchVote> findAll();
    List<MatchVote> findByMid(Integer mid);

    MatchVote findByMidAndUid(Integer mid, Integer uid);
}
