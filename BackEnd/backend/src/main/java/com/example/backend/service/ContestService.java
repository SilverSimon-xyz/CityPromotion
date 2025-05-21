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

    public Contest createContest(Contest contest, String authorFirstname, String authorLastname) {
        User author = userRepository.findByFirstnameAndLastname(authorFirstname, authorLastname).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        contest.setCreatedAt(new Date());
        contest.setAuthor(author);
        return contestRepository.save(contest);
    }

    public Contest updateContest(Long id, Contest contestDetails) {
        Optional<Contest> optionalContest = contestRepository.findById(id);
        if(optionalContest.isPresent()) {
            Contest contest = optionalContest.get()
                    .setName(contestDetails.getName())
                    .setDescription(contestDetails.getDescription())
                    .setRules(contestDetails.getRules())
                    .setGoal(contestDetails.getGoal())
                    .setPrize(contestDetails.getPrize())
                    .setDeadline(contestDetails.getDeadline())
                    .setActive(contestDetails.isActive())
                    .setUpdatedAt(new Date());
            return contestRepository.save(contest);
        } else {
            throw new EntityNotFoundException("User not found!");
        }

    }

    public List<Contest> getAllContest() {
        return contestRepository.findAll();
    }

    public Contest getContestById(Long id) {
        return contestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Contest Not Found!"));
    }

    public List<Contest> searchContestByName(String name) {
        if(name == null) return List.of();
        return contestRepository.searchByName(name);
    }

    public List<Contest> searchActiveContest() {
        return contestRepository.findAll().stream().filter(Contest::isActive).toList();
    }

    public Contest activeClosedContest(Long id) {
        Contest contest = contestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Contest Not Found!"));
        if(!contest.isActive()) contest.setActive(true);
        return contest;
    }

    public void deleteAllContest() {
        contestParticipationRepository.deleteAll();
        contestRepository.deleteAll();
    }

    public void deleteContest(Long id) {
        List<Long> participantsIds = contestParticipationRepository
                .findByContestId(id)
                .stream()
                .map(ContestParticipation::getId)
                .toList();
        contestParticipationRepository.deleteAllById(participantsIds);
        contestRepository.deleteById(id);
    }

    public List<ContestParticipation> getAllContestParticipant() {
        return contestParticipationRepository.findAll();
    }

    @Transactional
    public ContestParticipation participateContest(Long idContest, Long idUser, MediaFile mediaFile) {
        Optional<Contest> optionalContest = contestRepository.findById(idContest);
        Optional<User> optionalUser = userRepository.findById(idUser);

        if(optionalContest.isPresent() && optionalUser.isPresent()) {
            Contest contest = optionalContest.get();
            User user = optionalUser.get();
            if(contest.isActive()) {
                QuoteCriterion quoteCriterion = new QuoteCriterion();
                ContestParticipation participation = ContestParticipation.builder()
                        .contest(contest)
                        .user(user)
                        .mediaFile(mediaFile)
                        .quoteCriterion(quoteCriterion)
                        .build();
                contest.getParticipationContestList().add(participation);
                contest.setNumberOfParticipant(contest.getNumberOfParticipant()+1);
                mediaFileRepository.save(mediaFile);
                contestParticipationRepository.save(participation);
                contestRepository.save(contest);
                return participation;
            }
            return null;
        } else {
            throw new RuntimeException("Contest or User not Fount.");
        }
    }

    public ContestParticipation evaluateParticipant(Long idParticipant, QuoteCriterion quoteCriterionDetails) {
        Optional<ContestParticipation> optionalContestParticipation = contestParticipationRepository.findById(idParticipant);
        if(optionalContestParticipation.isPresent()) {
            ContestParticipation participant = optionalContestParticipation.get();
            if (!participant.getQuoteCriterion().isQuote()) {
                QuoteCriterion quoteCriterion = QuoteCriterion.builder()
                        .vote(quoteCriterionDetails.getVote())
                        .description(quoteCriterionDetails.getDescription())
                        .isQuote(true)
                        .build();
                participant.setQuoteCriterion(quoteCriterion);
                contestParticipationRepository.save(participant);
                return participant;
            }
            return null;
        } else {
            throw new RuntimeException("Participant non found.");
        }
    }

    public void deleteParticipant(Long idParticipant) {
        Optional<ContestParticipation> optionalContestParticipation = contestParticipationRepository.findById(idParticipant);
        if(optionalContestParticipation.isPresent()) {
            contestParticipationRepository.delete(optionalContestParticipation.get());
        } else {
            throw new RuntimeException("Participant non found.");
        }
    }

    public List<User> declareWinners(Long id) {
        Contest contest = contestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Contest not Found!"));
        int maxScore = contest.getParticipationContestList()
                .stream()
                .mapToInt(participateContest -> participateContest.getQuoteCriterion().getVote())
                .max()
                .orElse(0);
        List<User> winners = contest.getParticipationContestList()
                .stream()
                .filter(participateContest -> participateContest.getQuoteCriterion().getVote()==maxScore)
                .map(ContestParticipation::getUser)
                .toList();
        contest.setActive(false);
        contestRepository.save(contest);
        return winners;
    }

}
