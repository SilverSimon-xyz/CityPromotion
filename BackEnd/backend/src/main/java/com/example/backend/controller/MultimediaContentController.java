package com.example.backend.controller;

import com.example.backend.dto.response.MediaFileResponse;
import com.example.backend.dto.response.MultimediaContentResponse;
import com.example.backend.dto.request.MultimediaContentRequest;
import com.example.backend.dto.response.PointOfInterestResponse;
import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.content.Status;
import com.example.backend.entities.poi.PointOfInterest;
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
    public ResponseEntity<MultimediaContentResponse> uploadFile(@RequestBody MultimediaContentRequest request, @RequestParam int idPoi) {
        MultimediaContent multimediaContent = multimediaContentService.saveMultimediaContent(request.toMultimediaContent(), request.authorFirstName(), request.authorLastName(), request.mediaFile(), idPoi);
        MediaFile mediaFile = multimediaContent.getMediaFile();
        PointOfInterest pointOfInterest = multimediaContent.getPoi();
        MultimediaContentResponse response = MultimediaContentResponse
                .builder()
                .title(multimediaContent.getTitle())
                .type(multimediaContent.getType())
                .description(multimediaContent.getDescription())
                .author(multimediaContent.getAuthor().getName())
                .status(multimediaContent.getStatus())
                .mediaFileResponse(MediaFileResponse.builder()
                        .name(mediaFile.getName())
                        .type(mediaFile.getType())
                        .data(mediaFile.getData())
                        .build())
                .pointOfInterestResponse(PointOfInterestResponse.builder()
                        .name(pointOfInterest.getName())
                        .description(pointOfInterest.getDescription())
                        .author(pointOfInterest.getAuthor().getName())
                        .latitude(pointOfInterest.getLatitude())
                        .longitude(pointOfInterest.getLongitude())
                        .type(pointOfInterest.getType())
                        .openTime(pointOfInterest.getOpenTime())
                        .closeTime(pointOfInterest.getOpenTime())
                        .createdAt(pointOfInterest.getCreatedAt())
                        .updatedAt(pointOfInterest.getCreatedAt())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<MultimediaContentResponse> getContentDetails(@PathVariable int id) {
        MultimediaContent multimediaContent = multimediaContentService.getMultimediaContentById(id);
        MediaFile mediaFile = multimediaContent.getMediaFile();
        PointOfInterest pointOfInterest = multimediaContent.getPoi();
        MultimediaContentResponse response = MultimediaContentResponse
                .builder()
                .title(multimediaContent.getTitle())
                .type(multimediaContent.getType())
                .description(multimediaContent.getDescription())
                .author(multimediaContent.getAuthor().getName())
                .status(multimediaContent.getStatus())
                .mediaFileResponse(MediaFileResponse.builder()
                        .name(mediaFile.getName())
                        .type(mediaFile.getType())
                        .data(mediaFile.getData())
                        .build())
                .pointOfInterestResponse(PointOfInterestResponse.builder()
                        .name(pointOfInterest.getName())
                        .description(pointOfInterest.getDescription())
                        .author(pointOfInterest.getAuthor().getName())
                        .latitude(pointOfInterest.getLatitude())
                        .longitude(pointOfInterest.getLongitude())
                        .type(pointOfInterest.getType())
                        .openTime(pointOfInterest.getOpenTime())
                        .closeTime(pointOfInterest.getOpenTime())
                        .createdAt(pointOfInterest.getCreatedAt())
                        .updatedAt(pointOfInterest.getCreatedAt())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MultimediaContentResponse>> getAllContents() {
        return ResponseEntity.status(HttpStatus.OK).body(multimediaContentService.getAllMultimediaContent().stream()
                .map(multimediaContent -> MultimediaContentResponse.builder()
                        .title(multimediaContent.getTitle())
                        .type(multimediaContent.getType())
                        .description(multimediaContent.getDescription())
                        .author(multimediaContent.getAuthor().getName())
                        .status(multimediaContent.getStatus())
                        .mediaFileResponse(MediaFileResponse.builder()
                                .name(multimediaContent.getMediaFile().getName())
                                .type(multimediaContent.getMediaFile().getType())
                                .data(multimediaContent.getMediaFile().getData())
                                .build())
                        .pointOfInterestResponse(PointOfInterestResponse.builder()
                                .name(multimediaContent.getPoi().getName())
                                .description(multimediaContent.getPoi().getDescription())
                                .author(multimediaContent.getPoi().getAuthor().getName())
                                .latitude(multimediaContent.getPoi().getLatitude())
                                .longitude(multimediaContent.getPoi().getLongitude())
                                .type(multimediaContent.getPoi().getType())
                                .openTime(multimediaContent.getPoi().getOpenTime())
                                .closeTime(multimediaContent.getPoi().getOpenTime())
                                .createdAt(multimediaContent.getPoi().getCreatedAt())
                                .updatedAt(multimediaContent.getPoi().getCreatedAt())
                                .build())
                        .build())
                .toList());
    }

    @PutMapping("/edit/content/{idContent}")
    public ResponseEntity<MultimediaContentResponse> updateContent(@PathVariable int idContent, @RequestBody MultimediaContent multimediaContentDetails) {

        MultimediaContent multimediaContent = multimediaContentService.updateContent(idContent, multimediaContentDetails);
        MediaFile mediaFile = multimediaContent.getMediaFile();
        PointOfInterest pointOfInterest = multimediaContent.getPoi();
        MultimediaContentResponse response = MultimediaContentResponse
                .builder()
                .title(multimediaContent.getTitle())
                .type(multimediaContent.getType())
                .description(multimediaContent.getDescription())
                .author(multimediaContent.getAuthor().getName())
                .status(multimediaContent.getStatus())
                .mediaFileResponse(MediaFileResponse.builder()
                        .name(mediaFile.getName())
                        .type(mediaFile.getType())
                        .data(mediaFile.getData())
                        .build())
                .pointOfInterestResponse(PointOfInterestResponse.builder()
                        .name(pointOfInterest.getName())
                        .description(pointOfInterest.getDescription())
                        .author(pointOfInterest.getAuthor().getName())
                        .latitude(pointOfInterest.getLatitude())
                        .longitude(pointOfInterest.getLongitude())
                        .type(pointOfInterest.getType())
                        .openTime(pointOfInterest.getOpenTime())
                        .closeTime(pointOfInterest.getOpenTime())
                        .createdAt(pointOfInterest.getCreatedAt())
                        .updatedAt(pointOfInterest.getCreatedAt())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/edit/file/{idFile}")
    public ResponseEntity<MultimediaContentResponse> updateFile(@PathVariable int idFile, @RequestParam int idContent,
                                                                @RequestBody MediaFile mediaFile) {

        MultimediaContent multimediaContent = multimediaContentService.updateFile(idContent, idFile, mediaFile);
        MediaFile mediaFileExtract = multimediaContent.getMediaFile();
        PointOfInterest pointOfInterest = multimediaContent.getPoi();
        MultimediaContentResponse response = MultimediaContentResponse
                .builder()
                .title(multimediaContent.getTitle())
                .type(multimediaContent.getType())
                .description(multimediaContent.getDescription())
                .author(multimediaContent.getAuthor().getName())
                .status(multimediaContent.getStatus())
                .mediaFileResponse(MediaFileResponse.builder()
                        .name(mediaFileExtract.getName())
                        .type(mediaFileExtract.getType())
                        .data(mediaFileExtract.getData())
                        .build())
                .pointOfInterestResponse(PointOfInterestResponse.builder()
                        .name(pointOfInterest.getName())
                        .description(pointOfInterest.getDescription())
                        .author(pointOfInterest.getAuthor().getName())
                        .latitude(pointOfInterest.getLatitude())
                        .longitude(pointOfInterest.getLongitude())
                        .type(pointOfInterest.getType())
                        .openTime(pointOfInterest.getOpenTime())
                        .closeTime(pointOfInterest.getOpenTime())
                        .createdAt(pointOfInterest.getCreatedAt())
                        .updatedAt(pointOfInterest.getCreatedAt())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
                .map(multimediaContent -> MultimediaContentResponse.builder()
                        .title(multimediaContent.getTitle())
                        .type(multimediaContent.getType())
                        .description(multimediaContent.getDescription())
                        .author(multimediaContent.getAuthor().getName())
                        .status(multimediaContent.getStatus())
                        .mediaFileResponse(MediaFileResponse.builder()
                                .name(multimediaContent.getMediaFile().getName())
                                .type(multimediaContent.getMediaFile().getType())
                                .data(multimediaContent.getMediaFile().getData())
                                .build())
                        .pointOfInterestResponse(PointOfInterestResponse.builder()
                                .name(multimediaContent.getPoi().getName())
                                .description(multimediaContent.getPoi().getDescription())
                                .author(multimediaContent.getPoi().getAuthor().getName())
                                .latitude(multimediaContent.getPoi().getLatitude())
                                .longitude(multimediaContent.getPoi().getLongitude())
                                .type(multimediaContent.getPoi().getType())
                                .openTime(multimediaContent.getPoi().getOpenTime())
                                .closeTime(multimediaContent.getPoi().getOpenTime())
                                .createdAt(multimediaContent.getPoi().getCreatedAt())
                                .updatedAt(multimediaContent.getPoi().getCreatedAt())
                                .build())
                        .build())
                .toList());
    }

    @PatchMapping("/validate/{id}")
    public ResponseEntity<MultimediaContentResponse> validateMultimediaContent(@PathVariable int id, @RequestParam Status status) {
        MultimediaContent multimediaContent = multimediaContentService.validateMultimediaContent(id, status);
        MediaFile mediaFile = multimediaContent.getMediaFile();
        PointOfInterest pointOfInterest = multimediaContent.getPoi();
        MultimediaContentResponse response = MultimediaContentResponse
                .builder()
                .title(multimediaContent.getTitle())
                .type(multimediaContent.getType())
                .description(multimediaContent.getDescription())
                .author(multimediaContent.getAuthor().getName())
                .status(multimediaContent.getStatus())
                .mediaFileResponse(MediaFileResponse.builder()
                        .name(mediaFile.getName())
                        .type(mediaFile.getType())
                        .data(mediaFile.getData())
                        .build())
                .pointOfInterestResponse(PointOfInterestResponse.builder()
                        .name(pointOfInterest.getName())
                        .description(pointOfInterest.getDescription())
                        .author(pointOfInterest.getAuthor().getName())
                        .latitude(pointOfInterest.getLatitude())
                        .longitude(pointOfInterest.getLongitude())
                        .type(pointOfInterest.getType())
                        .openTime(pointOfInterest.getOpenTime())
                        .closeTime(pointOfInterest.getOpenTime())
                        .createdAt(pointOfInterest.getCreatedAt())
                        .updatedAt(pointOfInterest.getCreatedAt())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/validate/delete-all-rejected")
    public ResponseEntity<Void> deleteAllMultimediaContentRejected() {
        multimediaContentService.deleteAllMultimediaContentRejected();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
