package com.example.backend.entities.content;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "media_file")
public class MediaFile {

    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Lob
    private byte[] data; // For images, audio, video

}
