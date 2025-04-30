package com.example.backend.entities.contest;

import com.example.backend.entities.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
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
    @JoinColumn(name = "author", referencedColumnName = "name")
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

    @OneToMany(fetch = FetchType.EAGER)
    private final List<ContestParticipation> participationContestList = new ArrayList<>();

    public Contest() {

    }

    public Contest(Integer id, String name, String description, User author, String rules, String goal, String prize, LocalDate deadline, boolean active) {
        this.id = id;
        this.name = (name != null ) ? name : "Senza nome";
        this.description = description;
        this.author = author;
        this.rules = rules;
        this.goal = goal;
        this.prize = prize;
        this.deadline = deadline;
        this.active = active;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
