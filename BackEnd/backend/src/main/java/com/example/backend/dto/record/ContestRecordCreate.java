package com.example.backend.dto.record;


import com.example.backend.entities.contest.Contest;

import java.time.LocalDate;


public record ContestRecordCreate(String name, String description, String authorName, String rules, String goal, String prize,
                                  LocalDate deadline, boolean active, int numberParticipant) {

    public Contest toContest() {
        Contest contest = new Contest();
        contest.setName(name);
        contest.setDescription(description);
        contest.setRules(rules);
        contest.setGoal(goal);
        contest.setPrize(prize);
        contest.setDeadline(deadline);
        contest.setActive(active);
        return contest;
    }
}