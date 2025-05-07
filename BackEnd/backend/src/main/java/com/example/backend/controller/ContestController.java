package com.example.backend.controller;

import com.example.backend.dto.response.Account;
import com.example.backend.dto.response.ContestResponse;
import com.example.backend.dto.request.ContestRequest;
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

@RestController
@RequestMapping("/api/contest")
public class ContestController {

    @Autowired
    private ContestService contestService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('PRIVILEGE_CREATE')")
    public ResponseEntity<ContestResponse> addContest(@RequestBody ContestRequest request) {
        Contest contest = contestService.createContest(request.toContest(), request.authorName());
        return ResponseEntity.status(HttpStatus.CREATED).body(ContestResponse.mapContestToResponse(contest));
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('PRIVILEGE_EDIT')")
    public ResponseEntity<ContestResponse> updateContest(@PathVariable int id, @RequestBody Contest contestDetails) {
        Contest contest = contestService.updateContest(id, contestDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ContestResponse.mapContestToResponse(contest));
    }

    @PatchMapping("/edit/active")
    @PreAuthorize("hasAuthority('PRIVILEGE_EDIT')")
    public ResponseEntity<ContestResponse> reActiveContest(@PathVariable int id) {
        Contest contest = contestService.activeClosedContest(id);
        return ResponseEntity.status(HttpStatus.OK).body(ContestResponse.mapContestToResponse(contest));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('PRIVILEGE_READ')")
    public ResponseEntity<List<ContestResponse>> getAllContest() {
        return ResponseEntity.status(HttpStatus.OK).body(
                contestService.getAllContest()
                        .stream()
                        .map(ContestResponse::mapContestToResponse)
                        .toList());
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAuthority('PRIVILEGE_READ')")
    public ResponseEntity<ContestResponse> getContestById(@PathVariable int id) {
        Contest contest = contestService.getContestById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ContestResponse.mapContestToResponse(contest));
    }

    @GetMapping("/find/name")
    @PreAuthorize("hasAuthority('PRIVILEGE_READ')")
    public ResponseEntity<List<ContestResponse>> searchContestByName(@RequestParam String name) {
        List<Contest> contestList = contestService.searchContestByName(name);
        List<ContestResponse> contestResponseList = contestList
                .stream()
                .map(ContestResponse::mapContestToResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(contestResponseList);
    }

    @GetMapping("/find/active")
    @PreAuthorize("hasAuthority('PRIVILEGE_READ')")
    public ResponseEntity<List<ContestResponse>> searchActiveContest() {
        List<Contest> contestList = contestService.searchActiveContest();
        List<ContestResponse> contestResponseList = contestList
                .stream()
                .map(ContestResponse::mapContestToResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(contestResponseList);
    }

    @DeleteMapping("/delete/all")
    @PreAuthorize("hasAuthority('PRIVILEGE_DELETE')")
    public ResponseEntity<Void> deleteAllContest() {
        contestService.deleteAllContest();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('PRIVILEGE_DELETE')")
    public ResponseEntity<Void> deleteContest(@PathVariable int id) {
        contestService.deleteContest(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/participate/{idContest}/idUser")
    @PreAuthorize("hasAuthority('PRIVILEGE_PARTICIPATE')")
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
    @PreAuthorize("hasAuthority('PRIVILEGE_ENDING')")
    public ResponseEntity<List<Account>> declareWinners(@PathVariable int id) {
        List<User> winners = contestService.declareWinners(id);
        List<Account> winnersAccounts = winners
                .stream()
                .map(Account::new)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(winnersAccounts);
    }

}
