package com.example.backend.entities.contest;

import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.users.User;
import jakarta.persistence.*;

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
    @JoinColumn(name = "participant", referencedColumnName = "name", nullable = false)
    private User participant;

    @OneToOne
    @JoinColumn(name = "file_id")
    private MediaFile mediaFile;

    @Embedded
    private QuoteCriterion quoteCriterion;

    public ContestParticipation() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public User getParticipant() {
        return participant;
    }

    public void setParticipant(User participant) {
        this.participant = participant;
    }

    public MediaFile getMediaFile() {
        return mediaFile;
    }

    public void setMediaFile(MediaFile mediaFile) {
        this.mediaFile = mediaFile;
    }

    public QuoteCriterion getQuoteCriterion() {
        return quoteCriterion;
    }

    public void setQuoteCriterion(QuoteCriterion quoteCriterion) {
        this.quoteCriterion = quoteCriterion;
    }


}
