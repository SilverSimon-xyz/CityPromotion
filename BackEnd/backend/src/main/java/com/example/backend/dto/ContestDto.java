package com.example.backend.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ContestDto {

    private int id;

    private String name;

    private String description;

    private String author;

    private String rules;

    private String goal;

    private String prize;

    private LocalDate deadline;

    private boolean active;

    private Date dataCreation;

    private Date dataUpdate;

    private List<String> participationContestList;

    public ContestDto() {

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
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

    public List<String> getParticipationContestList() {
        return participationContestList;
    }

    public void setParticipationContestList(List<String> participationContestList) {
        this.participationContestList = participationContestList;
    }

    public Date getDataCreation() {
        return dataCreation;
    }

    public void setDataCreation(Date dataCreation) {
        this.dataCreation = dataCreation;
    }

    public Date getDataUpdate() {
        return dataCreation;
    }

    public void setDataUpdate(Date dataUpdate) {
        this.dataUpdate = dataUpdate;
    }
}
