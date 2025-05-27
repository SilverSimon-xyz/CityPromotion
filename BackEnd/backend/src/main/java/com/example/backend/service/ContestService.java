package com.example.backend.service;

import com.example.backend.dto.request.ContestRequest;
import com.example.backend.dto.request.QuoteCriterionRequest;
import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.users.User;
import com.example.backend.entities.contest.Contest;
import com.example.backend.entities.contest.ContestParticipant;
import com.example.backend.entities.contest.QuoteCriterion;
import com.example.backend.repository.ContestParticipantRepository;
import com.example.backend.repository.ContestRepository;
import com.example.backend.repository.MediaFileRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Contest createContest(ContestRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User author = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        Contest contest = Contest.builder()
                .name(request.name())
                .description(request.description())
                .rules(request.rules())
                .goal(request.goal())
                .prize(request.prize())
                .deadline(request.deadline())
                .active(request.active())
                .build().setAuthor(author).setCreatedAt(new Date());
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

    @Transactional
    public void deleteContest(Long id) {
        List<ContestParticipant> participants = contestParticipantRepository.findByContestId(id);
        for(ContestParticipant participant: participants) {
            if(participant.getMediaFile() != null) {
                mediaFileRepository.deleteById(participant.getMediaFile().getId());
            }
            contestParticipantRepository.deleteById(participant.getId());
        }
        contestRepository.deleteById(id);
    }

    @Transactional
    public List<ContestParticipant> getAllContestParticipant(Long idContest) {
        Contest contest = contestRepository.findById(idContest).orElseThrow(() -> new EntityNotFoundException("Contest not Found!"));
        return contest.getParticipationContestList();
    }

    @Transactional
    public ContestParticipant participateContest(Long idContest, MultipartFile file) throws IOException {
        Contest contest = contestRepository.findById(idContest).orElseThrow(() -> new EntityNotFoundException("Contest not Fount."));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        if(contestParticipantRepository.existsByContestAndUser(contest, user)) throw new EntityExistsException("User already sign up in this Contest!");
        if(contest.isActive()) {
            MediaFile mediaFile = MediaFile.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .size(file.getSize())
                    .data(file.getBytes())
                    .build();
            mediaFileRepository.save(mediaFile);
            QuoteCriterion quoteCriterion = new QuoteCriterion();
            ContestParticipant participant = ContestParticipant.builder()
                    .contest(contest)
                    .user(user)
                    .mediaFile(mediaFile)
                    .quoteCriterion(quoteCriterion)
                    .build();
            contest.getParticipationContestList().add(participant);
            contest.setNumberOfParticipant(contest.getNumberOfParticipant()+1);
            contestParticipantRepository.save(participant);
            contestRepository.save(contest);
            return participant;
        }
        return null;
    }

    public ContestParticipant getParticipant(Long idParticipant) {
        return contestParticipantRepository.findById(idParticipant).orElseThrow(() -> new EntityNotFoundException("Participant not found!"));
    }

    @Transactional
    public ContestParticipant evaluateParticipant(Long idContest, Long idParticipant, QuoteCriterionRequest request) {
        Contest contest = contestRepository.findById(idContest).orElseThrow(() -> new EntityNotFoundException("Contest Not Found!"));
        ContestParticipant participant = contestParticipantRepository.findById(idParticipant).orElseThrow(() -> new EntityNotFoundException("Participant not Found!"));
        if (!participant.getContest().getId().equals(contest.getId()))  throw new RuntimeException("Participant isn't sign up in this Contest!");
        if (participant.getQuoteCriterion().isQuote())  throw new RuntimeException("Participant already valuated!");
        QuoteCriterion quoteCriterion = QuoteCriterion.builder()
                .vote(request.vote())
                .description(request.description())
                .isQuote(true)
                .build();
        participant.setQuoteCriterion(quoteCriterion);
        contestParticipantRepository.save(participant);
        return participant;
    }

    @Transactional
    public void deleteParticipant(Long idContest, Long idParticipant) {
        Contest contest = contestRepository.findById(idContest).orElseThrow(() -> new EntityNotFoundException("Contest Not Found!"));
        ContestParticipant participant = contestParticipantRepository.findById(idParticipant).orElseThrow(() -> new EntityNotFoundException("Participant not found!"));
        if (!participant.getContest().getId().equals(contest.getId()))  throw new RuntimeException("Participant isn't sign up in this Contest!");

        MediaFile mediaFile = participant.getMediaFile();
        participant.setMediaFile(null);
        mediaFileRepository.deleteById(mediaFile.getId());

        contest.getParticipationContestList().remove(participant);
        contest.setNumberOfParticipant(contest.getNumberOfParticipant()-1);
        contestRepository.save(contest);

        contestParticipantRepository.deleteById(idParticipant);

    }

    @Transactional
    public List<ContestParticipant> declareWinners(Long id) {
        Contest contest = contestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Contest not Found!"));
        //if(contest.getDeadline().before(new Date())) throw new RuntimeException("The Contest is not expired");
        if(!contest.getParticipationContestList().stream().map(ContestParticipant::getQuoteCriterion).allMatch(QuoteCriterion::isQuote)) throw new RuntimeException("There are missing participants that weren't valuated!");

        int maxScore = contest.getParticipationContestList()
                .stream()
                .mapToInt(participateContest -> participateContest.getQuoteCriterion().getVote())
                .max()
                .orElse(0);
        List<ContestParticipant> winners = contest.getParticipationContestList()
                .stream()
                .filter(participateContest -> participateContest.getQuoteCriterion().getVote()==maxScore)
                .toList();
        contest.setActive(false);
        contestRepository.save(contest);
        return winners;
    }

}
