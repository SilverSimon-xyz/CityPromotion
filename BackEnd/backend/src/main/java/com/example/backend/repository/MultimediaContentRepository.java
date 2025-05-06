package com.example.backend.repository;

import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.content.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultimediaContentRepository extends JpaRepository<MultimediaContent, Integer> {

    List<MultimediaContent> findByStatus(Status status);

}
