package com.example.backend.dto.response;

import com.example.backend.entities.contest.Contest;
import lombok.Builder;

import java.util.Date;

@Builder
public record ContestResponse(Long id, String name, String description, AccountResponse author, String rules,
                              String goal, String prize,
                              Date deadline, boolean active,
                              Date createdAt, Date updatedAt, int numberOfParticipant) {

    public static ContestResponse mapToResponse(Contest contest) {
        AccountResponse authorResponse = AccountResponse.mapToResponse(contest.getAuthor());
        return ContestResponse.builder()
                .id(contest.getId())
                .name(contest.getName())
                .description(contest.getDescription())
                .author(authorResponse)
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
