package com.example.backend.dto.response;

import com.example.backend.entities.contest.ContestParticipant;
import com.example.backend.entities.contest.QuoteCriterion;
import lombok.Builder;

@Builder
public record ContestParticipantResponse(Long id, AccountResponse participant, ContestResponse contest, MediaFileResponse mediaFile, QuoteCriterion quoteCriterion) {

    public static ContestParticipantResponse mapToResponse(ContestParticipant participant) {
        AccountResponse participantResponse = AccountResponse.mapToResponse(participant.getUser());
        ContestResponse contestResponse = ContestResponse.mapToResponse(participant.getContest());
        MediaFileResponse mediaFileResponse = MediaFileResponse.mapToResponse(participant.getMediaFile());
        return ContestParticipantResponse.builder()
                .participant(participantResponse)
                .contest(contestResponse)
                .mediaFile(mediaFileResponse)
                .quoteCriterion(participant.getQuoteCriterion())
                .build();
    }
}
