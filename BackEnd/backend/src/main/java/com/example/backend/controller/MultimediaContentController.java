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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contents")
public class MultimediaContentController {

    @Autowired
    private MultimediaContentService multimediaContentService;

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('PRIVILEGE_LOAD')")
    public ResponseEntity<MultimediaContentResponse> uploadFile(@RequestBody MultimediaContentRequest request, @RequestParam int idPoi) {
        MultimediaContent multimediaContent = multimediaContentService.saveMultimediaContent(request.toMultimediaContent(), request.authorName(), request.mediaFile(), idPoi);
        return ResponseEntity.status(HttpStatus.OK).body(MultimediaContentResponse.mapContentToResponse(multimediaContent));
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAuthority('PRIVILEGE_READ')")
    public ResponseEntity<MultimediaContentResponse> getContentDetails(@PathVariable int id) {
        MultimediaContent multimediaContent = multimediaContentService.getMultimediaContentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(MultimediaContentResponse.mapContentToResponse(multimediaContent));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('PRIVILEGE_READ')")
    public ResponseEntity<List<MultimediaContentResponse>> getAllContents() {
        return ResponseEntity.status(HttpStatus.OK).body(multimediaContentService.getAllMultimediaContent().stream()
                .map(MultimediaContentResponse::mapContentToResponse)
                .toList());
    }

    @PutMapping("/edit/content/{idContent}")
    @PreAuthorize("hasAuthority('PRIVILEGE_UPDATE')")
    public ResponseEntity<MultimediaContentResponse> updateContent(@PathVariable int idContent, @RequestBody MultimediaContent multimediaContentDetails) {

        MultimediaContent multimediaContent = multimediaContentService.updateContent(idContent, multimediaContentDetails);
        return ResponseEntity.status(HttpStatus.OK).body(MultimediaContentResponse.mapContentToResponse(multimediaContent));
    }

    @PutMapping("/edit/file/{idFile}")
    @PreAuthorize("hasAuthority('PRIVILEGE_UPDATE')")
    public ResponseEntity<MultimediaContentResponse> updateFile(@PathVariable int idFile, @RequestParam int idContent,
                                                                @RequestBody MediaFile mediaFile) {

        MultimediaContent multimediaContent = multimediaContentService.updateFile(idContent, idFile, mediaFile);
        return ResponseEntity.status(HttpStatus.OK).body(MultimediaContentResponse.mapContentToResponse(multimediaContent));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('PRIVILEGE_DELETE')")
    public ResponseEntity<Void> deleteMultimediaContent(@PathVariable int id)  {
        multimediaContentService.deleteMultimediaContentById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete/all")
    @PreAuthorize("hasAuthority('PRIVILEGE_DELETE')")
    public ResponseEntity<Void> deleteAllMultimediaContent()  {
        multimediaContentService.deleteAllMultimediaContent();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/validate/view-all-pending")
    @PreAuthorize("hasAuthority('PRIVILEGE_VALIDATOR')")
    public ResponseEntity<List<MultimediaContentResponse>> getAllPendingMultimediaContent() {
        return ResponseEntity.status(HttpStatus.OK).body(multimediaContentService.getAllPendingMultimediaContent().stream()
                .map(MultimediaContentResponse::mapContentToResponse)
                .toList());
    }

    @PatchMapping("/validate/{id}")
    @PreAuthorize("hasAuthority('PRIVILEGE_VALIDATOR')")
    public ResponseEntity<MultimediaContentResponse> validateMultimediaContent(@PathVariable int id, @RequestParam Status status) {
        MultimediaContent multimediaContent = multimediaContentService.validateMultimediaContent(id, status);
        return ResponseEntity.status(HttpStatus.OK).body(MultimediaContentResponse.mapContentToResponse(multimediaContent));
    }

    @DeleteMapping("/validate/delete-all-rejected")
    @PreAuthorize("hasAuthority('PRIVILEGE_VALIDATOR')")
    public ResponseEntity<Void> deleteAllMultimediaContentRejected() {
        multimediaContentService.deleteAllMultimediaContentRejected();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
