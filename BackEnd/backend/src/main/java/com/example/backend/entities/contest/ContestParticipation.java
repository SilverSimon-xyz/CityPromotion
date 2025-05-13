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
    @Column(name = "participant_id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "contest_id", referencedColumnName = "contest_id", nullable = false)
    private Contest contest;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "participant_first_name", referencedColumnName = "firstname", nullable = false),
            @JoinColumn(name = "participant_last_name", referencedColumnName = "lastname", nullable = false)
    })
    private User participant;

    @OneToOne
    @JoinColumn(name = "file_id")
    private MediaFile mediaFile;

    @Embedded
    private QuoteCriterion quoteCriterion;


}
