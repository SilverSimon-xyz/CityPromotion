package com.example.backend.controller;

import com.example.backend.dto.response.MultimediaContentResponse;
import com.example.backend.dto.request.MultimediaContentRequest;
import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.content.Status;
import com.example.backend.service.MultimediaContentService;
import com.example.backend.utility.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<MultimediaContentResponse> uploadFile(@RequestBody MultimediaContentRequest request, @RequestParam int idPoi) {
        MultimediaContent multimediaContent = multimediaContentService.saveMultimediaContent(request.toMultimediaContent(), request.authorName(), request.mediaFile(), idPoi);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapContentToResponse(multimediaContent));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<MultimediaContentResponse> getContentDetails(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapContentToResponse(multimediaContentService.getMultimediaContentById(id)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MultimediaContentResponse>> getAllContents() {
        return ResponseEntity.status(HttpStatus.OK).body(multimediaContentService.getAllMultimediaContent().stream()
                .map(multimediaContent -> mapper.mapContentToResponse(multimediaContent))
                .toList());
    }

    @PutMapping("/edit/content/{idContent}")
    public ResponseEntity<MultimediaContentResponse> updateContent(@PathVariable int idContent, @RequestBody MultimediaContent multimediaContentDetails) throws IOException {

        MultimediaContent multimediaContent = multimediaContentService.updateContent(idContent, multimediaContentDetails);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapContentToResponse(multimediaContent));
    }

    @PutMapping("/edit/file/{idFile}")
    public ResponseEntity<MultimediaContentResponse> updateFile(@PathVariable int idFile, @RequestParam int idContent,
                                                                @RequestBody MediaFile mediaFile) throws IOException {

        MultimediaContent multimediaContent = multimediaContentService.updateFile(idContent, idFile, mediaFile);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapContentToResponse(multimediaContent));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMultimediaContent(@PathVariable int id)  {
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
                .map(multimediaContent -> mapper.mapContentToResponse(multimediaContent))
                .toList());
    }

    @PatchMapping("/validate/{id}")
    public ResponseEntity<MultimediaContentResponse> validateMultimediaContent(@PathVariable int id, @RequestParam Status status) {
        MultimediaContent multimediaContent = multimediaContentService.validateMultimediaContent(id, status);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapContentToResponse(multimediaContent));
    }

    @DeleteMapping("/validate/delete-all-rejected")
    public ResponseEntity<Void> deleteAllMultimediaContentRejected() {
        multimediaContentService.deleteAllMultimediaContentRejected();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
