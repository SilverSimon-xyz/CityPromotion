package com.example.backend.entities.content;

import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.users.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "multimediacontent")
public class MultimediaContent {

    @Id
    @Column(name = "mc_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private FormatFileType type;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "author_first_name", referencedColumnName = "firstname", nullable = false),
            @JoinColumn(name = "author_last_name", referencedColumnName = "lastname", nullable = false)
    })
    private User author;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, updatable = false)
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @OneToOne
    @JoinColumn(name = "file_id", nullable = false)
    private MediaFile mediaFile;

    @ManyToOne
    @JoinColumn(name = "poi_id", nullable = false)
    private PointOfInterest poi;


}
