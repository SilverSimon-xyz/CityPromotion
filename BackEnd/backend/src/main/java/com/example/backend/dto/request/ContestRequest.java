package com.example.backend.dto.request;


import com.example.backend.entities.contest.Contest;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;


public record ContestRequest(String name, String description, String authorFirstName, String authorLastName, String rules, String goal, String prize,
                             LocalDate deadline, boolean active, int numberParticipant) {

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