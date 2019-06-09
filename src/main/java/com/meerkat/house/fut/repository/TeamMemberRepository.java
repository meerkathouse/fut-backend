package com.meerkat.house.fut.repository;

import com.meerkat.house.fut.model.team_member.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Integer> {
    List<TeamMember> findAll();
    List<TeamMember> findAllByUid(Integer uid);
    List<TeamMember> findAllByTid(Integer tid);
    List<TeamMember> findAllByStatusAndUid(String status, Integer uid);

    TeamMember findByTidAndUid(Integer tid, Integer uid);
}
