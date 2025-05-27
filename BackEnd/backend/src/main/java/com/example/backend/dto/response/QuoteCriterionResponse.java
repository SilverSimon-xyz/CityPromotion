package com.example.backend.dto.response;

import com.example.backend.entities.contest.QuoteCriterion;
import lombok.Builder;

@Builder
public record QuoteCriterionResponse(int vote, String description, boolean isQuote) {

    public static QuoteCriterionResponse mapToResponse(QuoteCriterion quoteCriterion) {
        return QuoteCriterionResponse.builder()
                .vote(quoteCriterion.getVote())
                .description(quoteCriterion.getDescription())
                .isQuote(quoteCriterion.isQuote())
                .build();
    }
}
