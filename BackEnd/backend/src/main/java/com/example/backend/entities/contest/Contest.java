package com.example.backend.entities.contest;

import com.example.backend.entities.users.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "contest")
public class Contest {

    @Id
    @Column(name = "contest_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "author_first_name", referencedColumnName = "firstname", nullable = false),
            @JoinColumn(name = "author_last_name", referencedColumnName = "lastname", nullable = false)
    })
    private User author;

    @Column(nullable = false)
    private String rules;

    @Column(nullable = false)
    private String goal;

    @Column(nullable = false)
    private String prize;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, updatable = false)
    private Date updatedAt;

    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContestParticipation> participationContestList = new ArrayList<>();

    @Column(name = "number_participants", nullable = false, updatable = false)
    private int numberOfParticipant = 0;



}
