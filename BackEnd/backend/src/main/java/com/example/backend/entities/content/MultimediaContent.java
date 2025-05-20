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
    @Column(name = "mc_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private FormatFileType type;

    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToOne
    @JoinColumn(name = "file_id")
    private MediaFile mediaFile;

    @ManyToOne
    @JoinColumn(name = "poi_id")
    private PointOfInterest poi;


}
