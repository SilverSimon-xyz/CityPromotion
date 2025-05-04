package com.example.backend.repository;

import com.example.backend.entities.content.MultimediaContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultimediaContentRepository extends JpaRepository<MultimediaContent, Integer> {

}
