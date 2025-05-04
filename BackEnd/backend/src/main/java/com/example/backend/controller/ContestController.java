package com.example.backend.controller;

import com.example.backend.dto.Account;
import com.example.backend.dto.ContestDto;
import com.example.backend.dto.record.ContestRecordCreate;
import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.contest.Contest;
import com.example.backend.entities.contest.ContestParticipation;
import com.example.backend.entities.contest.QuoteCriterion;
import com.example.backend.entities.users.User;
import com.example.backend.service.ContestService;
import com.example.backend.utility.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/contest")
public class ContestController {

    @Autowired
    private ContestService contestService;
    @Autowired
    private Mapper mapper;

    @PostMapping("/add")
    public ResponseEntity<ContestDto> addContest(@RequestBody ContestRecordCreate request) {
        Contest contest = contestService.createContest(request.toContest(), request.authorName());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.mapContestToDto(contest));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ContestDto> updateContest(@PathVariable int id, @RequestBody Contest contestDetails) {
        Contest contest = contestService.updateContest(id, contestDetails);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapContestToDto(contest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContestDto>> getAllContest() {
        return ResponseEntity.status(HttpStatus.OK).body(
                contestService.getAllContest()
                        .stream()
                        .map(contest -> mapper.mapContestToDto(contest))
                        .toList());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ContestDto> getContestById(@PathVariable int id) {
        Contest contest = contestService.getContestById(id);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapContestToDto(contest));
    }

    @GetMapping("/find/name")
    public ResponseEntity<List<ContestDto>> searchContestByName(@RequestParam String name) {
        List<Contest> contestList = contestService.searchContestByName(name);
        List<ContestDto> contestDtoList = contestList
                .stream()
                .map(contest -> mapper.mapContestToDto(contest))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(contestDtoList);
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
    public ResponseEntity<Void> evaluateParticipant(@PathVariable int idParticipant, @RequestBody QuoteCriterion quoteCriterionDetails) {
        contestService.evaluateParticipant(idParticipant, quoteCriterionDetails);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/winners/{id}")
    public ResponseEntity<List<Account>> declareWinners(@PathVariable int id) {
        List<User> winners = contestService.declareWinners(id);
        List<Account> winnersAccounts = winners
                .stream()
                .map(winner -> mapper.mapUserToAccount(winner))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(winnersAccounts);
    }

}
