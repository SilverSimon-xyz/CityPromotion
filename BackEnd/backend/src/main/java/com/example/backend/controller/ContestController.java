package com.example.backend.controller;

import com.example.backend.dto.response.AccountResponse;
import com.example.backend.dto.response.ContestResponse;
import com.example.backend.dto.request.ContestRequest;
import com.example.backend.dto.response.RoleResponse;
import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.contest.Contest;
import com.example.backend.entities.contest.QuoteCriterion;
import com.example.backend.entities.users.User;
import com.example.backend.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contest")
public class ContestController {

    @Autowired
    private ContestService contestService;

    @PostMapping("/add")
    public ResponseEntity<ContestResponse> addContest(@RequestBody ContestRequest request) {
        Contest contest = contestService.createContest(request.toContest(), request.authorFirstName(), request.authorLastName());
        ContestResponse response = ContestResponse.builder()
                .name(contest.getName())
                .description(contest.getDescription())
                .author(contest.getAuthor().getName())
                .rules(contest.getRules())
                .goal(contest.getGoal())
                .prize(contest.getPrize())
                .deadline(contest.getDeadline())
                .active(contest.isActive())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ContestResponse> updateContest(@PathVariable int id, @RequestBody Contest contestDetails) {
        Contest contest = contestService.updateContest(id, contestDetails);
        ContestResponse response = ContestResponse.builder()
                .name(contest.getName())
                .description(contest.getDescription())
                .author(contest.getAuthor().getName())
                .rules(contest.getRules())
                .goal(contest.getGoal())
                .prize(contest.getPrize())
                .deadline(contest.getDeadline())
                .active(contest.isActive())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/edit/active")
    public ResponseEntity<ContestResponse> reActiveContest(@PathVariable int id) {
        Contest contest = contestService.activeClosedContest(id);
        ContestResponse response = ContestResponse.builder()
                .name(contest.getName())
                .description(contest.getDescription())
                .author(contest.getAuthor().getName())
                .rules(contest.getRules())
                .goal(contest.getGoal())
                .prize(contest.getPrize())
                .deadline(contest.getDeadline())
                .active(contest.isActive())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContestResponse>> getAllContest() {
        return ResponseEntity.status(HttpStatus.OK).body(
                contestService.getAllContest()
                        .stream()
                        .map(contest ->
                                ContestResponse.builder()
                                .name(contest.getName())
                                .description(contest.getDescription())
                                .author(contest.getAuthor().getName())
                                .rules(contest.getRules())
                                .goal(contest.getGoal())
                                .prize(contest.getPrize())
                                .deadline(contest.getDeadline())
                                .active(contest.isActive())
                                .build()
                        )
                        .toList());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ContestResponse> getContestById(@PathVariable int id) {
        Contest contest = contestService.getContestById(id);
        ContestResponse response = ContestResponse.builder()
                .name(contest.getName())
                .description(contest.getDescription())
                .author(contest.getAuthor().getName())
                .rules(contest.getRules())
                .goal(contest.getGoal())
                .prize(contest.getPrize())
                .deadline(contest.getDeadline())
                .active(contest.isActive())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/find/name")
    public ResponseEntity<List<ContestResponse>> searchContestByName(@RequestParam String name) {
        List<Contest> contestList = contestService.searchContestByName(name);
        List<ContestResponse> contestResponseList = contestList
                .stream()
                .map(contest ->
                        ContestResponse.builder()
                        .name(contest.getName())
                        .description(contest.getDescription())
                        .author(contest.getAuthor().getName())
                        .rules(contest.getRules())
                        .goal(contest.getGoal())
                        .prize(contest.getPrize())
                        .deadline(contest.getDeadline())
                        .active(contest.isActive())
                        .build()
                )
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(contestResponseList);
    }

    @GetMapping("/find/active")
    public ResponseEntity<List<ContestResponse>> searchActiveContest() {
        List<Contest> contestList = contestService.searchActiveContest();
        List<ContestResponse> contestResponseList = contestList
                .stream()
                .map(contest -> ContestResponse.builder()
                        .name(contest.getName())
                        .description(contest.getDescription())
                        .author(contest.getAuthor().getName())
                        .rules(contest.getRules())
                        .goal(contest.getGoal())
                        .prize(contest.getPrize())
                        .deadline(contest.getDeadline())
                        .active(contest.isActive())
                        .build()
                )
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(contestResponseList);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllContest() {
        contestService.deleteAllContest();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContest(@PathVariable int id) {
        contestService.deleteContest(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/participate/{idContest}/idUser")
    public ResponseEntity<Void> participateContest(@PathVariable int idContest, @RequestParam int idUser, @RequestBody MediaFile mediaFile) {
        contestService.participateContest(idContest, idUser, mediaFile);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/validate/{idParticipant}")
    @PreAuthorize("hasAuthority('PRIVILEGE_VALIDATOR')")
    public ResponseEntity<Void> evaluateParticipant(@PathVariable int idParticipant, @RequestBody QuoteCriterion quoteCriterionDetails) {
        contestService.evaluateParticipant(idParticipant, quoteCriterionDetails);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/winners/{id}")
    public ResponseEntity<List<AccountResponse>> declareWinners(@PathVariable int id) {
        List<User> winners = contestService.declareWinners(id);
        List<AccountResponse> winnersAccountResponses = winners
                .stream()
                .map(winner -> AccountResponse
                        .builder()
                        .id(winner.getId())
                        .name(winner.getName())
                        .email(winner.getEmail())
                        .password(winner.getPassword())
                        .roles(winner.getRoles().stream().map(
                                        role ->
                                                RoleResponse
                                                        .builder()
                                                        .name(role.getName())
                                                        .description(role.getDescription())
                                                        .build()
                                )
                                .collect(Collectors.toSet()))
                        .createdAt(winner.getCreatedAt())
                        .updatedAt(winner.getUpdatedAt())
                        .build())
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(winnersAccountResponses);
    }

}
