package com.example.backend.service;

import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.content.Status;
import com.example.backend.entities.users.User;
import com.example.backend.repository.MediaFileRepository;
import com.example.backend.repository.MultimediaContentRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.utility.MultimediaContentBuilder;
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

    public MultimediaContentService() {

    }

    public MultimediaContent saveMultimediaContent(MultimediaContent multimediaContentDetails, String authorName, MultipartFile file) throws IOException {

        User author = userRepository.findByName(authorName).orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        MediaFile mediaFile = new MediaFile();
        mediaFile.setName(file.getOriginalFilename());
        mediaFile.setType(file.getContentType());
        mediaFile.setData(file.getBytes());
        mediaFileRepository.save(mediaFile);

        MultimediaContent multimediaContent = MultimediaContentBuilder.build(multimediaContentDetails, author, mediaFile);

        multimediaContent.setCreatedAt(new Date());
        multimediaContent.setStatus(Status.PENDING);

        return multimediaContentRepository.save(multimediaContent);
    }

    public MultimediaContent updateMultimediaContent(int id, MultipartFile file) throws IOException {
        MultimediaContent multimediaContent = multimediaContentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Media not Found!"));
        //TODO
        return multimediaContentRepository.save(multimediaContent);
    }

    public MultimediaContent getMultimediaContentById(int id) {
        //TODO
        return multimediaContentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Media not Found!"));
    }

    public List<MultimediaContent> getAllMultimediaContent() {
        return multimediaContentRepository.findAll();
    }

    public void deleteMultimediaContentById(int id) {
        //TODO
        multimediaContentRepository.deleteById(id);
    }

    public void deleteAllMultimediaContent() {
        //TODO
        multimediaContentRepository.deleteAll();
    }

}
