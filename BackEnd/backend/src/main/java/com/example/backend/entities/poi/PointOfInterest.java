package com.example.backend.entities.poi;

import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.users.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pois")
public class PointOfInterest {

    @Id
    @Column(name = "poi_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description",nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "name")
    private User author;

    @Column(name = "lat", nullable = false)
    private double latitude;

    @Column(name = "lon", nullable = false)
    private double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PointOfInterestType type;

    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, updatable = false)
    private Date updatedAt;

    @OneToMany(mappedBy = "poi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MultimediaContent> multimediaContents = new ArrayList<>();

    public PointOfInterest() {

    }

    public PointOfInterest(Integer id, String name, String description, User author,
                           double lat, double lon, LocalTime openTime, LocalTime closeTime, PointOfInterestType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.latitude = lat;
        this.longitude = lon;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.type = type;
        this.createdAt = new Date();
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

    public LocalTime getOpenTime() { return openTime; }

    public void setOpenTime(LocalTime openTime) { this.openTime = openTime; }

    public LocalTime getCloseTime() { return closeTime; }

    public void setCloseTime(LocalTime closeTime) { this.closeTime = closeTime; }

    public PointOfInterestType getType() {
        return type;
    }

    public void setType(PointOfInterestType type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<MultimediaContent> getMultimediaContents() {
        return multimediaContents;
    }

    public void setMultimediaContents(List<MultimediaContent> multimediaContents) {
        this.multimediaContents = multimediaContents;
    }
}
