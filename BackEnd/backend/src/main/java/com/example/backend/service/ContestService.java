package com.example.backend.service;

import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.users.User;
import com.example.backend.entities.contest.Contest;
import com.example.backend.entities.contest.ContestParticipation;
import com.example.backend.entities.contest.QuoteCriterion;
import com.example.backend.repository.ContestParticipationRepository;
import com.example.backend.repository.ContestRepository;
import com.example.backend.repository.MediaFileRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Autowired
    private MediaFileRepository mediaFileRepository;

    public ContestService() {

    }

    public Contest createContest(Contest contest, String authorName) {
        User author = userRepository.findByName(authorName).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        contest.setCreatedAt(new Date());
        contest.setAuthor(author);
        return contestRepository.save(contest);
    }

    public Contest updateContest(int id, Contest contestDetails) {
        Contest contest = contestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        contest.setName(contestDetails.getName());
        contest.setDescription(contestDetails.getDescription());
        contest.setRules(contestDetails.getRules());
        contest.setGoal(contestDetails.getGoal());
        contest.setPrize(contestDetails.getPrize());
        contest.setDeadline(contestDetails.getDeadline());
        contest.setActive(contestDetails.getActive());
        contest.setUpdatedAt(new Date());
        return contestRepository.save(contest);
    }

    public List<Contest> getAllContest() {
        return contestRepository.findAll();
    }

    public Contest getContestById(int id) {
        return contestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Contest Not Found!"));
    }

    public List<Contest> searchContestByName(String name) {
        if(name == null) return List.of();
        return contestRepository.searchByName(name);
    }

    public List<Contest> searchActiveContest() {
        return contestRepository.findAll().stream().filter(Contest::getActive).toList();
    }

    public Contest activeClosedContest(int id) {
        Contest contest = contestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Contest Not Found!"));
        if(!contest.getActive()) contest.setActive(true);
        return contest;
    }

    public void deleteAllContest() {
        contestParticipationRepository.deleteAll();
        contestRepository.deleteAll();
    }

    public void deleteContest(int id) {
        List<Integer> participantsIds = contestParticipationRepository
                .findByContestId(id)
                .stream()
                .map(ContestParticipation::getId)
                .toList();
        contestParticipationRepository.deleteAllById(participantsIds);
        contestRepository.deleteById(id);
    }

    @Transactional
    public void participateContest(int idContest, int idUser, MediaFile mediaFile) {
        Optional<Contest> optionalContest = contestRepository.findById(idContest);
        Optional<User> optionalUser = userRepository.findById(idUser);

        if(optionalContest.isPresent() && optionalUser.isPresent()) {
            Contest contest = optionalContest.get();
            User user = optionalUser.get();
            if(contest.getActive()) {
                ContestParticipation participation = new ContestParticipation();
                QuoteCriterion quoteCriterion = new QuoteCriterion();
                participation.setContest(contest);
                participation.setParticipant(user);
                participation.setMediaFile(mediaFile);
                participation.setQuoteCriterion(quoteCriterion);
                contest.getParticipationContestList().add(participation);
                contest.setNumberOfParticipant(contest.getNumberOfParticipant()+1);
                mediaFileRepository.save(mediaFile);
                contestParticipationRepository.save(participation);
                contestRepository.save(contest);
            }
        } else {
            throw new RuntimeException("Contest or User not Fount.");
        }
    }

    public void evaluateParticipant(int idParticipant, QuoteCriterion quoteCriterionDetails) {
        Optional<ContestParticipation> optionalContestParticipation = contestParticipationRepository.findById(idParticipant);
        if(optionalContestParticipation.isPresent()) {
            ContestParticipation participation = optionalContestParticipation.get();
            if (!participation.getQuoteCriterion().getQuote()) {
                QuoteCriterion quoteCriterion = new QuoteCriterion();
                quoteCriterion.setVote(quoteCriterionDetails.getVote());
                quoteCriterion.setDescription(quoteCriterionDetails.getDescription());
                quoteCriterion.setQuote(true);
                participation.setQuoteCriterion(quoteCriterion);
                contestParticipationRepository.save(participation);
            }
        } else {
            throw new RuntimeException("Participant non found.");
        }
    }

    public List<User> declareWinners(int id) {
        Contest contest = contestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Contest not Found!"));
        int maxScore = contest.getParticipationContestList()
                .stream()
                .mapToInt(participateContest -> participateContest.getQuoteCriterion().getVote())
                .max()
                .orElse(0);
        List<User> winners = contest.getParticipationContestList()
                .stream()
                .filter(participateContest -> participateContest.getQuoteCriterion().getVote()==maxScore)
                .map(ContestParticipation::getParticipant)
                .toList();
        contest.setActive(false);
        contestRepository.save(contest);
        return winners;
    }

}
