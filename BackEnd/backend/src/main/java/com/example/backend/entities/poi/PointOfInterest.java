package com.example.backend.entities.poi;

import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.users.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinColumns({
            @JoinColumn(name = "author_first_name", referencedColumnName = "firstname", nullable = false),
            @JoinColumn(name = "author_last_name", referencedColumnName = "lastname", nullable = false)
    })
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

    @Override
    public String toString() {
        return "PointOfInterest{" +
                "author=" + author +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", type=" + type +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
