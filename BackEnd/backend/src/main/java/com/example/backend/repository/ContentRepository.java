package com.example.backend.repository;

import com.example.backend.entities.content.Content;
import com.example.backend.entities.content.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    boolean existsByTitle(String title);
    List<Content> findByStatus(Status status);

}
