package com.example.backend.controller;

import com.example.backend.dto.request.QuoteCriterionRequest;
import com.example.backend.dto.response.ContestParticipantResponse;
import com.example.backend.dto.response.ContestResponse;
import com.example.backend.dto.request.ContestRequest;
import com.example.backend.entities.contest.Contest;
import com.example.backend.entities.contest.ContestParticipant;
import com.example.backend.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ANIMATOR')")
    public ResponseEntity<ContestResponse> addContest(@RequestBody ContestRequest request) {
        Contest contest = contestService.createContest(request);
        ContestResponse response = ContestResponse.mapToResponse(contest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ANIMATOR') or hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ANIMATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteContest(@PathVariable Long id) {
        contestService.deleteContest(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/participant/{idContest}/all")
    public ResponseEntity<List<ContestParticipantResponse>> getAllParticipants(@PathVariable Long idContest) {
        return ResponseEntity.status(HttpStatus.OK).body(
                contestService.getAllContestParticipant(idContest)
                        .stream()
                        .map(ContestParticipantResponse::mapToResponse)
                        .toList());
    }

    @PostMapping("/participant/participate")
    public ResponseEntity<ContestParticipantResponse> participateContest(@RequestParam Long idContest,  @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        ContestParticipant participant = contestService.participateContest(idContest, file);
        ContestParticipantResponse response = ContestParticipantResponse.mapToResponse(participant);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/participant/delete/{idContest}/{idParticipant}")
    @PreAuthorize("hasRole('ANIMATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long idContest, @PathVariable Long idParticipant) {
        contestService.deleteParticipant(idContest, idParticipant);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/participant/{idParticipant}")
    public ResponseEntity<ContestParticipantResponse> getParticipant(@PathVariable Long idParticipant)  {
        ContestParticipant participant = contestService.getParticipant(idParticipant);
        ContestParticipantResponse response = ContestParticipantResponse.mapToResponse(participant);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/participant/{idContest}/{idParticipant}/validate")
    @PreAuthorize("hasRole('ANIMATOR') or hasRole('ADMIN')")
    public ResponseEntity<ContestParticipantResponse> evaluateParticipant(
            @PathVariable Long idContest,
            @PathVariable Long idParticipant,
            @RequestBody QuoteCriterionRequest request) {

        ContestParticipant participant = contestService.evaluateParticipant(idContest, idParticipant, request);
        ContestParticipantResponse response = ContestParticipantResponse.mapToResponse(participant);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/participant/winners/{id}")
    @PreAuthorize("hasRole('ANIMATOR')")
    public ResponseEntity<List<ContestParticipantResponse>> declareWinners(@PathVariable Long id) {
        List<ContestParticipant> winners = contestService.declareWinners(id);
        List<ContestParticipantResponse> winnersAccountResponses = winners
                .stream()
                .map(ContestParticipantResponse::mapToResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(winnersAccountResponses);
    }

}
