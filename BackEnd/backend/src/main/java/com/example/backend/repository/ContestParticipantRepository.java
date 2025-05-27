package com.example.backend.repository;

import com.example.backend.entities.contest.Contest;
import com.example.backend.entities.contest.ContestParticipant;
import com.example.backend.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestParticipantRepository extends JpaRepository<ContestParticipant, Long> {
    List<ContestParticipant> findByContestId(Long id);
    boolean existsByContestAndUser(Contest contest, User user);
}
