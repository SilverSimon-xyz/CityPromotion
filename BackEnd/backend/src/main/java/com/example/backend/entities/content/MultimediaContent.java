package com.example.backend.entities.content;

import com.example.backend.entities.users.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
public class MultimediaContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mc_id")
    private int id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @ManyToOne(cascade = CascadeType.MERGE)
    private User author;
    @Enumerated(EnumType.STRING)
    @Column(name = "format", nullable = false)
    private FormatFileEnum format;
    @Column(name = "duration", nullable = false)
    private float duration;
    @Column(name = "dimension", nullable = false)
    private float dimension;
    @Column(name = "resolution", nullable = false)
    private float resolution;
    @CreationTimestamp
    @Column(name = "data-creation", nullable = false, updatable = false)
    private Date dataCreation;
    @UpdateTimestamp
    @Column(name = "data-update", nullable = false, updatable = false)
    private Date dataUpdate;
    @Column(name = "status", nullable = false)
    private Status status;
    /**
    @ManyToOne(fetch = FetchType.EAGER)
    private PointOfInterest pointOfInterest;
*/
    public MultimediaContent() {
        super();
    }

    public MultimediaContent(String title, String description, User author, FormatFileEnum format,
                             float duration, float dimension, float resolution) {
        this.title = (title != null ) ? title : "Senza nome";
        this.description = description;
        this.author = author;
        this.format = format;
        this.duration = duration;
        this.dimension = dimension;
        this.resolution = resolution;
        this.dataCreation = new Date();
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public FormatFileEnum getFormat() {
        return format;
    }
    public void setFormat(FormatFileEnum format) {
        this.format = format;
    }

    public float getDuration() { return duration; }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public float getDimension() { return dimension; }

    public void setDimension(float dimension) {
        this.dimension = dimension;
    }

    public float getResolution() { return resolution; }

    public void setResolution(float resolution) {
        this.resolution = resolution;
    }

    public Date getDataCreation() {
        return dataCreation;
    }

    public void setDataCreation(Date dataCreation) {
        this.dataCreation = dataCreation;
    }

    public Date getDataUpdate() {
        return dataCreation;
    }

    public void setDataUpdate(Date dataUpdate) {
        this.dataUpdate = dataUpdate;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

}
