package com.example.backend.service;

import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.content.Status;
import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.users.User;
import com.example.backend.repository.MediaFileRepository;
import com.example.backend.repository.MultimediaContentRepository;
import com.example.backend.repository.PointOfInterestRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.utility.builder.MultimediaContentBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class MultimediaContentService {

    @Autowired
    private MultimediaContentRepository multimediaContentRepository;
    @Autowired
    private MediaFileRepository mediaFileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PointOfInterestRepository poiRepository;

    public MultimediaContentService() {

    }

    public MultimediaContent saveMultimediaContent(MultimediaContent multimediaContentDetails, String authorName, MultipartFile file, int idPoi) throws IOException {

        User author = userRepository.findByName(authorName).orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        PointOfInterest poi = poiRepository.findById(idPoi).orElseThrow(() -> new EntityNotFoundException("POI Not Found"));

        MediaFile mediaFile = new MediaFile();
        mediaFile.setName(file.getOriginalFilename());
        mediaFile.setType(file.getContentType());
        mediaFile.setData(file.getBytes());
        mediaFileRepository.save(mediaFile);

        MultimediaContent multimediaContent = MultimediaContentBuilder.build(multimediaContentDetails, author, mediaFile, poi);

        poiRepository.save(poi);
        multimediaContent.setCreatedAt(new Date());
        multimediaContent.setStatus(Status.PENDING);

        return multimediaContentRepository.save(multimediaContent);
    }

    public MultimediaContent updateMultimediaContent(int idContent, int idFile, MultipartFile file, MultimediaContent multimediaContentDetails) throws IOException {
        MediaFile mediaFile = mediaFileRepository.findById(idFile).orElseThrow(() -> new EntityNotFoundException("Media File not Found!"));

        mediaFile.setName(file.getOriginalFilename());
        mediaFile.setType(file.getContentType());
        mediaFile.setData(file.getBytes());
        mediaFileRepository.save(mediaFile);

        MultimediaContent multimediaContent = multimediaContentRepository.findById(idContent).orElseThrow(() -> new EntityNotFoundException("Content not Found!"));

        multimediaContent.setTitle(multimediaContentDetails.getTitle());
        multimediaContent.setDescription(multimediaContentDetails.getDescription());
        multimediaContent.setUpdatedAt(new Date());
        multimediaContent.setMediaFile(mediaFile);
        multimediaContent.setStatus(Status.APPROVED);

        return multimediaContentRepository.save(multimediaContent);
    }

    public MultimediaContent getMultimediaContentById(int id) {
        return multimediaContentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Multimedia Content not Found!"));
    }

    public List<MultimediaContent> getAllMultimediaContent() {
        return multimediaContentRepository.findAll();
    }

    public void deleteMultimediaContentById(int id) {
        MultimediaContent multimediaContent = multimediaContentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Multimedia Content not Found!"));
        PointOfInterest pointOfInterest = multimediaContent.getPoi();
        pointOfInterest.getMultimediaContents().remove(multimediaContent);
        poiRepository.save(pointOfInterest);
        multimediaContentRepository.deleteById(id);
    }

    public void deleteAllMultimediaContent() {
        List<PointOfInterest> pointOfInterestList = poiRepository.findAll();
        pointOfInterestList.forEach(poi -> poi.getMultimediaContents().clear());
        poiRepository.saveAll(pointOfInterestList);
        multimediaContentRepository.deleteAll();
    }

}
