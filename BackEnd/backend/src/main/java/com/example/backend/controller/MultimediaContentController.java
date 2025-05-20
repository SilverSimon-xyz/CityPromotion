package com.example.backend.controller;

import com.example.backend.dto.response.MultimediaContentResponse;
import com.example.backend.dto.request.MultimediaContentRequest;
import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.content.Status;
import com.example.backend.service.MultimediaContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contents")
public class MultimediaContentController {

    @Autowired
    private MultimediaContentService multimediaContentService;

    @PostMapping("/upload")
    public ResponseEntity<MultimediaContentResponse> uploadFile(@RequestBody MultimediaContentRequest request, @RequestParam Long idPoi) {
        MultimediaContent multimediaContent = multimediaContentService.saveMultimediaContent(request.toMultimediaContent(), request.authorFirstName(), request.authorLastName(), request.mediaFile(), idPoi);
        MultimediaContentResponse response = MultimediaContentResponse.mapToResponse(multimediaContent);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<MultimediaContentResponse> getContentDetails(@PathVariable Long id) {
        MultimediaContent multimediaContent = multimediaContentService.getMultimediaContentById(id);
        MultimediaContentResponse response = MultimediaContentResponse.mapToResponse(multimediaContent);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MultimediaContentResponse>> getAllContents() {
        return ResponseEntity.status(HttpStatus.OK).body(multimediaContentService.getAllMultimediaContent().stream()
                .map(MultimediaContentResponse::mapToResponse)
                .toList());
    }

    @PutMapping("/edit/content/{idContent}")
    public ResponseEntity<MultimediaContentResponse> updateContent(@PathVariable Long idContent, @RequestBody MultimediaContent multimediaContentDetails) {
        MultimediaContent multimediaContent = multimediaContentService.updateContent(idContent, multimediaContentDetails);
        MultimediaContentResponse response = MultimediaContentResponse.mapToResponse(multimediaContent);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/edit/file/{idFile}")
    public ResponseEntity<MultimediaContentResponse> updateFile(@PathVariable Long idFile, @RequestParam Long idContent,
                                                                @RequestBody MediaFile mediaFile) {

        MultimediaContent multimediaContent = multimediaContentService.updateFile(idContent, idFile, mediaFile);
        MultimediaContentResponse response = MultimediaContentResponse.mapToResponse(multimediaContent);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMultimediaContent(@PathVariable Long id)  {
        multimediaContentService.deleteMultimediaContentById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllMultimediaContent()  {
        multimediaContentService.deleteAllMultimediaContent();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/validate/view-all-pending")
    public ResponseEntity<List<MultimediaContentResponse>> getAllPendingMultimediaContent() {
        return ResponseEntity.status(HttpStatus.OK).body(multimediaContentService.getAllPendingMultimediaContent().stream()
                .map(MultimediaContentResponse::mapToResponse)
                .toList());
    }

    @PatchMapping("/validate/{id}")
    public ResponseEntity<MultimediaContentResponse> validateMultimediaContent(@PathVariable Long id, @RequestParam Status status) {
        MultimediaContent multimediaContent = multimediaContentService.validateMultimediaContent(id, status);
        MultimediaContentResponse response = MultimediaContentResponse.mapToResponse(multimediaContent);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/validate/delete-all-rejected")
    public ResponseEntity<Void> deleteAllMultimediaContentRejected() {
        multimediaContentService.deleteAllMultimediaContentRejected();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
