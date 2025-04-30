package com.example.backend.entities.contest;

import jakarta.persistence.Embeddable;

@Embeddable
public class QuoteCriterion {

    private int vote;
    private String description;
    private boolean isQuote = false;

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getQuote() {
        return isQuote;
    }

    public void setQuote(boolean isQuote) {
        this.isQuote = isQuote;
    }
}
