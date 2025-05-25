package com.example.backend.controller;

import com.example.backend.entities.content.MediaFile;
import com.example.backend.repository.MediaFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/media")
public class MediaFileController {

    @Autowired
    private MediaFileRepository mediaFileRepository;

    @GetMapping("/get/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        MediaFile mediaFile = mediaFileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found!"));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(mediaFile.getType()))
                .body(mediaFile.getData());
    }
}
