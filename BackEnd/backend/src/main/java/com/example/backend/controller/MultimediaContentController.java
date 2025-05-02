package com.example.backend.controller;

import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.service.MultimediaContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mc")
public class MultimediaContentController {

    @Autowired
    private MultimediaContentService multimediaContentService;

    public ResponseEntity<byte[]> getFile(int id) {
        MultimediaContent media = multimediaContentService.getMultimediaContentById(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(media.getType()))
                .body(media.getData());
    }
}
