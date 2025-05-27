package com.example.backend.entities.contest;

import com.example.backend.entities.users.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @Column(name = "contest_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    private String rules;

    private String goal;

    private String prize;

    private Date deadline;

    private boolean active;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "contest", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<ContestParticipant> participationContestList = new ArrayList<>();

    @Column(name = "number_participants")
    private int numberOfParticipant = 0;

}
