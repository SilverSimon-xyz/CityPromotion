package com.example.backend.entities.contest;

import com.example.backend.entities.users.User;
import com.example.backend.entities.content.MultimediaContent;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ContestParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "participant_id", referencedColumnName = "name", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "contest", referencedColumnName = "name", nullable = false)
    private Contest contest;
    @ManyToOne
    @JoinColumn(name = "participant", referencedColumnName = "name", nullable = false)
    private User participant;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mc_id")
    private List<MultimediaContent> multimediaContentList;
    @Embedded
    private QuoteCriterion quoteCriterion;

    public ContestParticipation() {
        this.multimediaContentList = new ArrayList<>();
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

    public List<MultimediaContent> getMultimediaContentList() {
        return multimediaContentList;
    }

    public QuoteCriterion getQuoteCriterion() {
        return quoteCriterion;
    }

    public void setQuoteCriterion(QuoteCriterion quoteCriterion) {
        this.quoteCriterion = quoteCriterion;
    }
}
