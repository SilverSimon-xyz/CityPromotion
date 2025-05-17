package com.example.backend.dto.request;


import com.example.backend.entities.contest.Contest;
import java.util.Date;


public record ContestRequest(String name, String description, String authorFirstName, String authorLastName, String rules, String goal, String prize,
                             Date deadline, boolean active, int numberParticipant) {

    public Contest toContest() {

        return Contest.builder()
                .name(name)
                .description(description)
                .rules(rules)
                .goal(goal)
                .prize(prize)
                .deadline(deadline)
                .active(active)
                .build();
    }
}