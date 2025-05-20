package com.example.backend.repository;

import com.example.backend.entities.contest.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {

    List<Contest> searchByName(String name);

}
