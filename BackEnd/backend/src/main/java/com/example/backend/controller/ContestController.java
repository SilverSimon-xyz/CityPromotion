package com.example.backend.controller;

import com.example.backend.dto.response.AccountResponse;
import com.example.backend.dto.response.ContestParticipationResponse;
import com.example.backend.dto.response.ContestResponse;
import com.example.backend.dto.request.ContestRequest;
import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.contest.Contest;
import com.example.backend.entities.contest.ContestParticipation;
import com.example.backend.entities.contest.QuoteCriterion;
import com.example.backend.entities.users.User;
import com.example.backend.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contest")
public class ContestController {

    @Autowired
    private ContestService contestService;

    @PostMapping("/add")
    public ResponseEntity<ContestResponse> addContest(@RequestBody ContestRequest request) {
        Contest contest = contestService.createContest(request.toContest(), request.authorFirstname(), request.authorLastname());
        ContestResponse response = ContestResponse.mapToResponse(contest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ContestResponse> updateContest(@PathVariable Long id, @RequestBody Contest contestDetails) {
        Contest contest = contestService.updateContest(id, contestDetails);
        ContestResponse response = ContestResponse.mapToResponse(contest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/edit/active")
    public ResponseEntity<ContestResponse> reActiveContest(@PathVariable Long id) {
        Contest contest = contestService.activeClosedContest(id);
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

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllContest() {
        contestService.deleteAllContest();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContest(@PathVariable Long id) {
        contestService.deleteContest(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/participant/all")
    public ResponseEntity<List<ContestParticipationResponse>> getAllParticipants() {
        return ResponseEntity.status(HttpStatus.OK).body(
                contestService.getAllContestParticipant()
                        .stream()
                        .map(ContestParticipationResponse::mapToResponse)
                        .toList());
    }

    @PostMapping("/participant/participate/{idContest}/idUser")
    public ResponseEntity<ContestParticipationResponse> participateContest(@PathVariable Long idContest, @RequestParam Long idUser, @RequestBody MediaFile mediaFile) {
        ContestParticipation participant = contestService.participateContest(idContest, idUser, mediaFile);
        ContestParticipationResponse response = ContestParticipationResponse.mapToResponse(participant);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/participant/delete/{idParticipant}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long idParticipant, @RequestBody QuoteCriterion quoteCriterionDetails) {
        contestService.deleteParticipant(idParticipant);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/participant/validate/{idParticipant}")
    public ResponseEntity<ContestParticipationResponse> evaluateParticipant(@PathVariable Long idParticipant, @RequestBody QuoteCriterion quoteCriterionDetails) {
        ContestParticipation participant = contestService.evaluateParticipant(idParticipant, quoteCriterionDetails);
        ContestParticipationResponse response = ContestParticipationResponse.mapToResponse(participant);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/participant/winners/{id}")
    public ResponseEntity<List<AccountResponse>> declareWinners(@PathVariable Long id) {
        List<User> winners = contestService.declareWinners(id);
        List<AccountResponse> winnersAccountResponses = winners
                .stream()
                .map(AccountResponse::mapToResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(winnersAccountResponses);
    }

}
