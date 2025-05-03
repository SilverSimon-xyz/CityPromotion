package com.example.backend.repository;

import com.example.backend.entities.contest.ContestParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestParticipationRepository extends JpaRepository<ContestParticipation, Integer> {
    List<ContestParticipation> findByContestId(int id);
}
