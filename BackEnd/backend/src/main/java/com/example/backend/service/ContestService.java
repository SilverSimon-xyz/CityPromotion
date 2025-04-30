package com.example.backend.service;

import com.example.backend.entities.User;
import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.contest.Contest;
import com.example.backend.entities.contest.ContestParticipation;
import com.example.backend.repository.ContestParticipationRepository;
import com.example.backend.repository.ContestRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ContestService {


    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContestParticipationRepository contestParticipationRepository;



    public Contest createContest(Contest contest) {
        //TODO
        return null;
    }

    public Contest updateContest(int idContest, Contest contest) {
        //TODO
        return null;
    }

    public List<Contest> getAllContest() {
        return contestRepository.findAll();
    }

    public Contest getContestById(int id) {
        return contestRepository.findById(id).get();
    }

    public List<Contest> searchContestByName(String name) {
        if(name == null) return List.of();
        return contestRepository.searchByName(name);
    }

    public List<Contest> searchContestByDescription(String description) {
        if(description == null) return List.of();
        return contestRepository.searchByDescription(description);
    }

    public void participateContest(int idContest, int idUser, List<MultimediaContent> multimediaContentList) {
        //TODO
    }

    public void deleteParticipationContest(ContestParticipation participation, String reason) {
        //TODO
    }

    public void evaluateParticipant(int idParticipant, int vote, String description) {
        //TODO
    }

    public List<User> declareWinners(int idContest) {
        //TODO
        return null;
    }


}
