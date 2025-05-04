package com.example.backend.dto;

import com.example.backend.entities.content.Status;

public class MultimediaContentDto {

    private int id;

    private String title;

    private String type;

    private String description;

    private String author;

    private Status status;

    private MediaFileDto mediaFileDto;

    private String poiName;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getPOIName() {
        return poiName;
    }

    public void setPOIName(String poiName) {
        this.poiName = poiName;
    }
}
