package com.example.backend.dto;

import com.example.backend.entities.content.FormatFileType;
import com.example.backend.entities.content.Status;

public class MultimediaContentDto {

    private int id;

    private String title;

    private FormatFileType type;

    private String description;

    private String author;

    private Status status;

    private MediaFileDto mediaFileDto;

    private PointOfInterestDto pointOfInterestDto;

    public MultimediaContentDto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public FormatFileType getType() {
        return type;
    }

    public void setType(FormatFileType type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public MediaFileDto getMediaFile() {
        return mediaFileDto;
    }

    public void setMediaFile(MediaFileDto mediaFileDto) {
        this.mediaFileDto = mediaFileDto;
    }

    public PointOfInterestDto getPOI() {
        return pointOfInterestDto;
    }

    public void setPOI(PointOfInterestDto pointOfInterestDto) {
        this.pointOfInterestDto = pointOfInterestDto;
    }
}
