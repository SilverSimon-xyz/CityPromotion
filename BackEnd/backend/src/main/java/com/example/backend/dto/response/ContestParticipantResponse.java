package com.example.backend.dto.response;

import com.example.backend.entities.contest.ContestParticipant;
import lombok.Builder;

@Builder
public record ContestParticipantResponse(Long id, AccountResponse participant, ContestResponse contest, MediaFileResponse mediaFile, QuoteCriterionResponse quoteCriterion) {

    public static ContestParticipantResponse mapToResponse(ContestParticipant participant) {
        AccountResponse participantResponse = AccountResponse.mapToResponse(participant.getUser());
        ContestResponse contestResponse = ContestResponse.mapToResponse(participant.getContest());
        MediaFileResponse mediaFileResponse = MediaFileResponse.mapToResponse(participant.getMediaFile());
        QuoteCriterionResponse quoteCriterionResponse = QuoteCriterionResponse.mapToResponse(participant.getQuoteCriterion());
        return ContestParticipantResponse.builder()
                .id(participant.getId())
                .participant(participantResponse)
                .contest(contestResponse)
                .mediaFile(mediaFileResponse)
                .quoteCriterion(quoteCriterionResponse)
                .build();
    }
}
