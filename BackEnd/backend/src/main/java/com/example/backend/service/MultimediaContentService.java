package com.example.backend.service;

import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.content.Status;
import com.example.backend.entities.users.User;
import com.example.backend.repository.MultimediaContentRepository;
import com.example.backend.repository.UserRepository;
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
    private UserRepository userRepository;

    public MultimediaContent addMultimediaContent(MultipartFile file, User author, String description) throws IOException {
        MultimediaContent media = new MultimediaContent();
        media.setTitle(file.getName());
        media.setType(file.getContentType());
        media.setDescription(description);
        media.setAuthor(author);
        media.setData(file.getBytes());
        media.setCreatedAt(new Date());
        media.setStatus(Status.PENDING);
        return multimediaContentRepository.save(media);
    }

    public MultimediaContent updateMultimediaContent(int id, MultipartFile file) {
        //TODO
        return null;
    }

    public MultimediaContent getMultimediaContentById(int id) {
        return multimediaContentRepository.findById(id).orElseThrow();
    }

    public List<MultimediaContent> getAllMultimediaContent() {
        return multimediaContentRepository.findAll();
    }

    public void deleteMultimediaContentById(int id) {
        //TODO
    }

    public void deleteAllMultimediaContent() {
        //TODO
    }

}
