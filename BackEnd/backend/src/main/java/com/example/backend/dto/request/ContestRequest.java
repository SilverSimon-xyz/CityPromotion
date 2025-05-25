package com.example.backend.dto.request;

import java.util.Date;


public record ContestRequest(String name, String description, String rules, String goal, String prize, Date deadline, boolean active) {

}