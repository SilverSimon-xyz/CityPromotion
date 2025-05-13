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
import com.example.backend.utility.MultimediaContentBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public MultimediaContent saveMultimediaContent(MultimediaContent multimediaContentDetails, String authorFirstName, String authorLastName, MediaFile mediaFile, int idPoi) {

        User author = userRepository.findByFirstNameAndLastName(authorFirstName, authorLastName).orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        PointOfInterest poi = poiRepository.findById(idPoi).orElseThrow(() -> new EntityNotFoundException("POI Not Found"));

        mediaFileRepository.save(mediaFile);

        MultimediaContent multimediaContent = MultimediaContentBuilder.build(multimediaContentDetails, author, mediaFile, poi);

        poiRepository.save(poi);
        multimediaContent.setCreatedAt(new Date());
        multimediaContent.setStatus(Status.PENDING);

        return multimediaContentRepository.save(multimediaContent);
    }

    public MultimediaContent updateContent(int idContent, MultimediaContent multimediaContentDetails) {

        Optional<MultimediaContent> optionalMultimediaContent = multimediaContentRepository.findById(idContent);
        if(optionalMultimediaContent.isPresent()) {
            MultimediaContent multimediaContent = optionalMultimediaContent.get()
                    .setTitle(multimediaContentDetails.getTitle())
                    .setType(multimediaContentDetails.getType())
                    .setDescription(multimediaContentDetails.getDescription())
                    .setStatus(Status.APPROVED)
                    .setUpdatedAt(new Date());

            return multimediaContentRepository.save(multimediaContent);
        } else {
            throw new EntityNotFoundException("Content not Found!");
        }

    }

    public MultimediaContent updateFile(int idContent, int idFile, MediaFile mediaFileDetails) {
        MediaFile mediaFile = mediaFileRepository.findById(idFile).orElseThrow(() -> new EntityNotFoundException("Media File not Found!"));
        mediaFile
                .setName(mediaFileDetails.getName())
                .setType(mediaFileDetails.getType())
                .setData(mediaFileDetails.getData());

        mediaFileRepository.save(mediaFile);

        MultimediaContent multimediaContent = multimediaContentRepository.findById(idContent).orElseThrow(() -> new EntityNotFoundException("Content not Found!"));

        multimediaContent.setUpdatedAt(new Date())
                .setMediaFile(mediaFile)
                .setStatus(Status.APPROVED);

        return multimediaContentRepository.save(multimediaContent);
    }

    public MultimediaContent getMultimediaContentById(int id) {
        return multimediaContentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Multimedia Content not Found!"));
    }

    public List<MultimediaContent> getAllMultimediaContent() {
        List<MultimediaContent> multimediaContentList = multimediaContentRepository.findAll();
        multimediaContentList.removeIf(multimediaContent -> multimediaContent.getStatus()==Status.REJECTED || multimediaContent.getStatus()==Status.PENDING);
        return multimediaContentList;
    }

    public List<MultimediaContent> getAllPendingMultimediaContent() {
        return multimediaContentRepository.findByStatus(Status.PENDING);
    }

    public void deleteMultimediaContentById(int id) {
        MultimediaContent multimediaContent = multimediaContentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Multimedia Content not Found!"));
        PointOfInterest pointOfInterest = multimediaContent.getPoi();
        pointOfInterest.getMultimediaContents().remove(multimediaContent);
        poiRepository.save(pointOfInterest);
        MediaFile mediaFile = multimediaContent.getMediaFile();
        mediaFileRepository.delete(mediaFile);
        multimediaContentRepository.deleteById(id);
    }

    public void deleteAllMultimediaContent() {
        List<MultimediaContent> multimediaContentList = multimediaContentRepository.findAll();
        List<PointOfInterest> pointOfInterestList = poiRepository.findAll();
        pointOfInterestList.forEach(poi -> poi.getMultimediaContents().clear());
        poiRepository.saveAll(pointOfInterestList);
        multimediaContentList.forEach(multimediaContent -> {
            MediaFile mediaFile = multimediaContent.getMediaFile();
            mediaFileRepository.delete(mediaFile);
        });
        multimediaContentRepository.deleteAll();
    }

    public MultimediaContent validateMultimediaContent(int id, Status status) {
        MultimediaContent multimediaContent = multimediaContentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Multimedia Content not Found!"));
        switch(status) {
            case PENDING -> throw new IllegalArgumentException("You have to APPROVED or REJECTED");
            case APPROVED, REJECTED -> {
                    multimediaContent.setStatus(status);
                    multimediaContentRepository.save(multimediaContent);
            }
            default -> throw new RuntimeException("No such state exist!");
        }
        return multimediaContent;
    }

    public void deleteAllMultimediaContentRejected() {
        List<MultimediaContent> multimediaContentList = multimediaContentRepository.findByStatus(Status.REJECTED);
        List<PointOfInterest> pointOfInterestList = poiRepository.findAll();
        pointOfInterestList.forEach(poi -> poi.getMultimediaContents().removeIf(multimediaContent -> multimediaContent.getStatus().equals(Status.REJECTED)));
        poiRepository.saveAll(pointOfInterestList);
        multimediaContentList.forEach(multimediaContent -> {
            MediaFile mediaFile = multimediaContent.getMediaFile();
            mediaFileRepository.delete(mediaFile);
        });
        multimediaContentRepository.deleteAll(multimediaContentList);

    }

}
