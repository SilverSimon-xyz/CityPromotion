package com.example.backend.entities.contest;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class QuoteCriterion {

    private int vote;
    private String description;
    private boolean isQuote = false;


}
