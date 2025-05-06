package com.example.backend.dto.response;

import com.example.backend.entities.contest.Contest;

import java.time.LocalDate;
import java.util.Date;

public record ContestResponse(int id, String name, String description, String author, String rules,
                              String goal, String prize, LocalDate deadline,
                              boolean active, Date createdAt, Date updatedAt, int numberOfParticipant) {

    public static ContestResponse mapContestToResponse(Contest contest) {

        return new ContestResponse(contest.getId(), contest.getName(), contest.getDescription(), contest.getAuthor().getName(),
                contest.getRules(), contest.getGoal(), contest.getPrize(), contest.getDeadline(), contest.getActive(),
                contest.getCreatedAt(), contest.getDataUpdated(), contest.getNumberOfParticipant()
        );
    }
}
