package com.example.backend.entities.contest;

import com.example.backend.entities.users.User;
import com.example.backend.entities.content.MultimediaContent;
import jakarta.persistence.*;

@Entity
@Table(name = "contest_participation")
public class ContestParticipation {

    @Id
    @JoinColumn(name = "participant_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "contest_id", referencedColumnName = "contest_id", nullable = false)
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "participant", referencedColumnName = "name", nullable = false)
    private User participant;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mc_id", referencedColumnName = "mc_id")
    private MultimediaContent multimediaContent;

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

    public MultimediaContent getMultimediaContent() {
        return multimediaContent;
    }
    public void setMultimediaContent(MultimediaContent multimediaContent) {
        this.multimediaContent = multimediaContent;
    }

    public QuoteCriterion getQuoteCriterion() {
        return quoteCriterion;
    }

    public void setQuoteCriterion(QuoteCriterion quoteCriterion) {
        this.quoteCriterion = quoteCriterion;
    }

    @PostPersist
    public void addParticipant() {
        if(contest != null) {
            contest.updateParticipantNumber();
        }
    }

    @PostRemove
    public void deleteParticipant() {
        if(contest != null) {
            contest.updateParticipantNumber();
        }
    }
}
