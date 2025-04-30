package com.example.backend.repository;

import com.example.backend.entities.contest.ContestParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestParticipationRepository extends JpaRepository<ContestParticipation, Integer> {
}
