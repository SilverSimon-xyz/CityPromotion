package com.example.backend.entities.contest;

import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.users.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contest_participation")
public class ContestParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contest_id", referencedColumnName = "contest_id")
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "file_id")
    private MediaFile mediaFile;

    @Embedded
    private QuoteCriterion quoteCriterion;


}
