package com.example.backend.service;

import com.example.backend.dto.request.ContentRequest;
import com.example.backend.dto.request.StatusRequest;
import com.example.backend.entities.content.Content;
import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.content.Status;
import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.users.User;
import com.example.backend.repository.ContentRepository;
import com.example.backend.repository.MediaFileRepository;
import com.example.backend.repository.PointOfInterestRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PointOfInterestRepository poiRepository;
    @Autowired
    private MediaFileRepository mediaFileRepository;

    public ContentService() {

    }

    @Transactional
    public Content createContent(ContentRequest request, MultipartFile file) throws IOException {
        if(contentRepository.existsByTitle(request.title())) throw new EntityExistsException("Content already present!");
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User author = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        PointOfInterest pointOfInterest = poiRepository.findById(request.idPoi()).orElseThrow(() -> new EntityNotFoundException("POI Not Found"));
        MediaFile mediaFile = MediaFile.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .data(file.getBytes())
                .build();
        mediaFileRepository.save(mediaFile);
        Content content = Content.builder()
                .title(request.title())
                .content(request.content())
                .hashtag(request.hashtag())
                .build()
                .setAuthor(author)
                .setPoi(pointOfInterest)
                .setMediaFile(mediaFile)
                .setStatus(Status.PENDING)
                .setCreatedAt(new Date());
        contentRepository.save(content);
        pointOfInterest.getContents().add(content);
        poiRepository.save(pointOfInterest);
        return content;
    }

    public Content updateContent(Long idContent, ContentRequest request) {
        Optional<Content> optionalMultimediaContent = contentRepository.findById(idContent);
        PointOfInterest pointOfInterest = poiRepository.findById(request.idPoi()).orElseThrow(() -> new EntityNotFoundException("POI Not Found"));
        if(optionalMultimediaContent.isPresent()) {
            Content content = optionalMultimediaContent.get()
                    .setTitle(request.title())
                    .setContent(request.content())
                    .setPoi(pointOfInterest)
                    .setStatus(Status.APPROVED)
                    .setUpdatedAt(new Date());

            return contentRepository.save(content);
        } else {
            throw new EntityNotFoundException("Content not Found!");
        }

    }

    public Content getContentById(Long id) {
        return contentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Content not Found!"));
    }

    public List<Content> getAllContent() {
        return contentRepository.findAll();
    }

    @Transactional
    public void deleteContentById(Long id) {
        Content content = contentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Content not Found!"));

        MediaFile mediaFile = content.getMediaFile();
        mediaFileRepository.deleteById(mediaFile.getId());
        content.setMediaFile(null);

        PointOfInterest pointOfInterest = content.getPoi();
        pointOfInterest.getContents().remove(content);
        poiRepository.save(pointOfInterest);

        contentRepository.delete(content);
    }

    public Content validateContent(Long id, StatusRequest status) {
        Content content = contentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Content not Found!"));
        switch(status.status()) {
            case PENDING -> throw new IllegalArgumentException("You have to APPROVED or REJECTED");
            case APPROVED, REJECTED -> {
                    content.setStatus(status.status());
                    contentRepository.save(content);
            }
            default -> throw new RuntimeException("No such state exist!");
        }
        return content;
    }

    @Transactional
    public void deleteAllContentRejected() {
        List<Content> contentList = contentRepository.findByStatus(Status.REJECTED);

        List<MediaFile> mediaFileList = contentList.stream().map(Content::getMediaFile).toList();
        contentList.forEach(content -> content.setMediaFile(null));

        mediaFileRepository.deleteAll(mediaFileList);

        List<PointOfInterest> pointOfInterestList = contentList.stream().map(Content::getPoi).toList();
        pointOfInterestList.forEach(poi -> poi.getContents().removeIf(multimediaContent -> multimediaContent.getStatus().equals(Status.REJECTED)));
        poiRepository.saveAll(pointOfInterestList);

        contentRepository.deleteAll(contentList);
    }

}
