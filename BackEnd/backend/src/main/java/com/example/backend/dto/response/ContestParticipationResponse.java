package com.example.backend.dto.response;


import com.example.backend.entities.contest.ContestParticipation;
import com.example.backend.entities.contest.QuoteCriterion;
import lombok.Builder;

@Builder
public record ContestParticipationResponse(String participant, String contestName, MediaFileResponse mediaFileResponse, QuoteCriterion quoteCriterion) {

    public static ContestParticipationResponse mapToResponse(ContestParticipation participant) {
        return ContestParticipationResponse.builder()
                .participant(participant.getParticipant().getName())
                .contestName(participant.getContest().getName())
                .mediaFileResponse(MediaFileResponse.mapToResponse(participant.getMediaFile()))
                .quoteCriterion(participant.getQuoteCriterion())
                .build();
    }
}
