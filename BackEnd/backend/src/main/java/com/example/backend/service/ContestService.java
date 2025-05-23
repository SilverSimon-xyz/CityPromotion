package com.example.backend.service;

import com.example.backend.dto.request.ContestParticipantRequest;
import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.users.User;
import com.example.backend.entities.contest.Contest;
import com.example.backend.entities.contest.ContestParticipant;
import com.example.backend.entities.contest.QuoteCriterion;
import com.example.backend.repository.ContestParticipantRepository;
import com.example.backend.repository.ContestRepository;
import com.example.backend.repository.MediaFileRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private ContestParticipantRepository contestParticipantRepository;
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

    public List<Contest> searchContest(String name) {
        return (name != null && !name.isEmpty())? contestRepository.findByNameContainingIgnoreCase(name):
                contestRepository.findAll();
    }

    public void deleteContest(Long id) {
        List<Long> participantsIds = contestParticipantRepository
                .findByContestId(id)
                .stream()
                .map(ContestParticipant::getId)
                .toList();
        contestParticipantRepository.deleteAllById(participantsIds);
        contestRepository.deleteById(id);
    }

    public List<ContestParticipant> getAllContestParticipant() {
        return contestParticipantRepository.findAll();
    }

    @Transactional
    public ContestParticipant participateContest(ContestParticipantRequest request, MultipartFile file) throws IOException {
        Optional<Contest> optionalContest = contestRepository.findById(request.idContest());
        Optional<User> optionalUser = userRepository.findById(request.idUser());

        if(optionalContest.isPresent() && optionalUser.isPresent()) {
            Contest contest = optionalContest.get();
            User user = optionalUser.get();
            if(contest.isActive()) {
                MediaFile mediaFile = MediaFile.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .size(file.getSize())
                        .data(file.getBytes())
                        .build();
                mediaFileRepository.save(mediaFile);
                QuoteCriterion quoteCriterion = new QuoteCriterion();
                ContestParticipant participation = ContestParticipant.builder()
                        .contest(contest)
                        .user(user)
                        .mediaFile(mediaFile)
                        .quoteCriterion(quoteCriterion)
                        .build();
                contestParticipantRepository.save(participation);
                contest.getParticipationContestList().add(participation);
                contest.setNumberOfParticipant(contest.getNumberOfParticipant()+1);
                contestRepository.save(contest);
                return participation;
            }
            return null;
        } else {
            throw new RuntimeException("Contest or User not Fount.");
        }
    }

    public ContestParticipant evaluateParticipant(Long idParticipant, QuoteCriterion quoteCriterionDetails) {
        Optional<ContestParticipant> optionalContestParticipation = contestParticipantRepository.findById(idParticipant);
        if(optionalContestParticipation.isPresent()) {
            ContestParticipant participant = optionalContestParticipation.get();
            if (!participant.getQuoteCriterion().isQuote()) {
                QuoteCriterion quoteCriterion = QuoteCriterion.builder()
                        .vote(quoteCriterionDetails.getVote())
                        .description(quoteCriterionDetails.getDescription())
                        .isQuote(true)
                        .build();
                participant.setQuoteCriterion(quoteCriterion);
                contestParticipantRepository.save(participant);
                return participant;
            }
            return null;
        } else {
            throw new RuntimeException("Participant non found.");
        }
    }

    public void deleteParticipant(Long idParticipant) {
        Optional<ContestParticipant> optionalContestParticipation = contestParticipantRepository.findById(idParticipant);
        if(optionalContestParticipation.isPresent()) {
            ContestParticipant participant = optionalContestParticipation.get();
            Contest contest = participant.getContest();
            contest.setNumberOfParticipant(contest.getNumberOfParticipant()-1);
            MediaFile mediaFile = participant.getMediaFile();
            mediaFileRepository.delete(mediaFile);
            contestParticipantRepository.delete(participant);

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
                .map(ContestParticipant::getUser)
                .toList();
        contest.setActive(false);
        contestRepository.save(contest);
        return winners;
    }

}
