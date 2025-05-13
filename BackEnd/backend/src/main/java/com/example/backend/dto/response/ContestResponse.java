package com.example.backend.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Date;

@Builder
public record ContestResponse(String name, String description, String author, String rules,
                              String goal, String prize, LocalDate deadline,
                              boolean active, Date createdAt, Date updatedAt, int numberOfParticipant) {

}
