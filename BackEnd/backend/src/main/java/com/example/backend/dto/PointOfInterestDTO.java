package com.example.backend.dto;
import com.example.backend.entities.User;
import com.example.backend.entities.enums.PointOfInterestType;
import com.example.backend.entities.enums.Status;

import java.time.LocalTime;

public class PointOfInterestDto {

    private int id;
    private String name;
    private String description;
    private User author;
    private Status status;
    private double latitude;
    private double longitude;
    private PointOfInterestType type;
    private LocalTime openTime;
    private LocalTime closeTime;

    public PointOfInterestDto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public PointOfInterestType getType() {
        return type;
    }

    public void setType(PointOfInterestType type) {
        this.type = type;
    }
}
