package com.example.backend.controller;

import com.example.backend.dto.MultimediaContentDto;
import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.service.MultimediaContentService;
import com.example.backend.utility.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/contents")
public class MultimediaContentController {

    @Autowired
    private MultimediaContentService multimediaContentService;
    @Autowired
    private Mapper mapper;

    @PostMapping("/upload")
    public ResponseEntity<MultimediaContentDto> uploadFile(@RequestBody MultimediaContent content, @RequestParam("file")MultipartFile file) throws IOException {
        MultimediaContent multimediaContent = multimediaContentService.saveMultimediaContent(content, content.getAuthor().getName(), file);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapContentToDto(multimediaContent));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<MultimediaContentDto> getContentDetails(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapContentToDto(multimediaContentService.getMultimediaContentById(id)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MultimediaContentDto>> getAllContents() {
        return ResponseEntity.status(HttpStatus.OK).body(multimediaContentService.getAllMultimediaContent()
                .stream()
                .map(multimediaContent -> mapper.mapContentToDto(multimediaContent))
                .toList()
        );
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateFile()  {
        //TODO: modify return type, add parameters
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFile()  {
        //TODO: modify return type, add parameters
        return null;
    }
}
