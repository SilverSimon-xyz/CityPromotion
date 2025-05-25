package com.example.backend.controller;

import com.example.backend.dto.request.ContestParticipantRequest;
import com.example.backend.dto.response.AccountResponse;
import com.example.backend.dto.response.ContestParticipantResponse;
import com.example.backend.dto.response.ContestResponse;
import com.example.backend.dto.request.ContestRequest;
import com.example.backend.entities.contest.Contest;
import com.example.backend.entities.contest.ContestParticipant;
import com.example.backend.entities.contest.QuoteCriterion;
import com.example.backend.entities.users.User;
import com.example.backend.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/contest")
public class ContestController {

    @Autowired
    private ContestService contestService;

    @PostMapping("/add")
    public ResponseEntity<ContestResponse> addContest(@RequestBody ContestRequest request) {
        Contest contest = contestService.createContest(request);
        ContestResponse response = ContestResponse.mapToResponse(contest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ContestResponse> updateContest(@PathVariable Long id, @RequestBody Contest contestDetails) {
        Contest contest = contestService.updateContest(id, contestDetails);
        ContestResponse response = ContestResponse.mapToResponse(contest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContestResponse>> getAllContest() {
        return ResponseEntity.status(HttpStatus.OK).body(
                contestService.getAllContest()
                        .stream()
                        .map(ContestResponse::mapToResponse)
                        .toList());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ContestResponse> getContestById(@PathVariable Long id) {
        Contest contest = contestService.getContestById(id);
        ContestResponse response = ContestResponse.mapToResponse(contest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContestResponse>> searchContest(@RequestParam(required = false) String name) {
        List<Contest> contestList = contestService.searchContest(name);
        List<ContestResponse> contestResponseList = contestList
                .stream()
                .map(ContestResponse::mapToResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(contestResponseList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContest(@PathVariable Long id) {
        contestService.deleteContest(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/participant/all")
    public ResponseEntity<List<ContestParticipantResponse>> getAllParticipants() {
        return ResponseEntity.status(HttpStatus.OK).body(
                contestService.getAllContestParticipant()
                        .stream()
                        .map(ContestParticipantResponse::mapToResponse)
                        .toList());
    }

    @PostMapping("/participant/participate")
    public ResponseEntity<ContestParticipantResponse> participateContest(@RequestPart("data") ContestParticipantRequest request,  @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        ContestParticipant participant = contestService.participateContest(request, file);
        ContestParticipantResponse response = ContestParticipantResponse.mapToResponse(participant);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/participant/delete/{idParticipant}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long idParticipant) {
        contestService.deleteParticipant(idParticipant);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/participant/validate/{idParticipant}")
    public ResponseEntity<ContestParticipantResponse> evaluateParticipant(@PathVariable Long idParticipant, @RequestBody QuoteCriterion quoteCriterionDetails) {
        ContestParticipant participant = contestService.evaluateParticipant(idParticipant, quoteCriterionDetails);
        ContestParticipantResponse response = ContestParticipantResponse.mapToResponse(participant);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/participant/winners/{id}")
    public ResponseEntity<List<AccountResponse>> declareWinners(@PathVariable Long id) {
        List<User> winners = contestService.declareWinners(id);
        List<AccountResponse> winnersAccountResponses = winners
                .stream()
                .map(AccountResponse::mapToResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(winnersAccountResponses);
    }

}
