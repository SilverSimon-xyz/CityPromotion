package com.example.backend.dto.response;

import com.example.backend.entities.contest.Contest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.util.Date;

@Builder
public record ContestResponse(String name, String description, String author, String rules,
                              String goal, String prize,
                              Date deadline, boolean active,
                              Date createdAt, Date updatedAt, int numberOfParticipant) {

    public static ContestResponse mapToResponse(Contest contest) {
        return ContestResponse.builder()
                .name(contest.getName())
                .description(contest.getDescription())
                .author(contest.getAuthor().getName())
                .rules(contest.getRules())
                .goal(contest.getGoal())
                .prize(contest.getPrize())
                .deadline(contest.getDeadline())
                .active(contest.isActive())
                .createdAt(contest.getCreatedAt())
                .updatedAt(contest.getUpdatedAt())
                .build();
    }
}
