package com.example.backend.dto.request;

public record ContentRequest(String title, String content, String hashtag, String authorFirstname, String authorLastname, Long idPoi) {

}
