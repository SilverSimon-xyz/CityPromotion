package com.example.backend.entities;

import com.example.backend.entities.enums.PointOfInterestType;
import com.example.backend.entities.enums.Status;
import jakarta.persistence.*;

import java.time.LocalTime;


@Entity
@Table(name = "pois")
public class PointOfInterest {
    @Id
    @Column(name = "poi_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User author;
    @Column(name = "lat", nullable = false)
    private double latitude;
    @Column(name = "lon", nullable = false)
    private double longitude;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PointOfInterestType type;
    @Column(nullable = false)
    private LocalTime openTime;
    @Column(nullable = false)
    private LocalTime closeTime;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public PointOfInterest() {

    }

    public PointOfInterest(Integer id, String name, String description, User author, Status status,
                           double lat, double lon, LocalTime openTime, LocalTime closeTime, PointOfInterestType type) {
        this.id = id;
        this.name = (name != null ) ? name : "Senza nome";
        this.description = description;
        this.author = author;
        this.status = status;
        this.latitude = lat;
        this.longitude = lon;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.type = type;
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

    public boolean isPublished() {
        return status == Status.APPROVED || status == Status.REPORTED;
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
}
