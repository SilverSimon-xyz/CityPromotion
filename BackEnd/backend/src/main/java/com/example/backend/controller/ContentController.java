package com.example.backend.controller;

import com.example.backend.dto.response.ContentResponse;
import com.example.backend.dto.request.ContentRequest;
import com.example.backend.entities.content.Content;
import com.example.backend.entities.content.Status;
import com.example.backend.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/contents")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @PostMapping("/add")
    public ResponseEntity<ContentResponse> createContent(@RequestPart("data") ContentRequest request, @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        Content content = contentService.createContent(request, file);
        System.out.println("Successo!");
        ContentResponse response = ContentResponse.mapToResponse(content);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ContentResponse> getContentDetails(@PathVariable Long id) {
        Content content = contentService.getContentById(id);
        ContentResponse response = ContentResponse.mapToResponse(content);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContentResponse>> getAllContent() {
        return ResponseEntity.status(HttpStatus.OK).body(contentService.getAllContent().stream()
                .map(ContentResponse::mapToResponse)
                .toList());
    }

    @PutMapping("/edit/content/{idContent}")
    public ResponseEntity<ContentResponse> updateContent(@PathVariable Long idContent, @RequestBody ContentRequest request) {
        Content content = contentService.updateContent(idContent, request);
        ContentResponse response = ContentResponse.mapToResponse(content);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id)  {
        contentService.deleteContentById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PatchMapping("/validate/{id}")
    public ResponseEntity<ContentResponse> validateContent(@PathVariable Long id, @RequestParam Status status) {
        Content content = contentService.validateContent(id, status);
        ContentResponse response = ContentResponse.mapToResponse(content);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/validate/delete-all-rejected")
    public ResponseEntity<Void> deleteAllContentRejected() {
        contentService.deleteAllContentRejected();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
