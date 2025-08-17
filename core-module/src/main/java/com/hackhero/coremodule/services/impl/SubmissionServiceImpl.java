package com.hackhero.coremodule.services.impl;

import com.hackhero.coremodule.dto.requests.CreateSubmissionRequest;
import com.hackhero.coremodule.dto.requests.UpdateSubmissionRequest;
import com.hackhero.coremodule.dto.responses.SubmissionResponse;
import com.hackhero.coremodule.repositories.ChallengeRepository;
import com.hackhero.coremodule.repositories.HackathonRepository;
import com.hackhero.coremodule.repositories.SubmissionRepository;
import com.hackhero.coremodule.repositories.TeamRepository;
import com.hackhero.coremodule.services.SubmissionService;
import com.hackhero.coremodule.utils.mapper.SubmissionMapper;
import com.hackhero.domainmodule.entities.Challenge;
import com.hackhero.domainmodule.entities.Hackathon;
import com.hackhero.domainmodule.entities.Submission;
import com.hackhero.domainmodule.entities.Team;
import com.hackhero.domainmodule.enums.HackathonStatus;
import com.hackhero.domainmodule.enums.SubmissionStatus;
import com.hackhero.domainmodule.exceptions.ChallengeNotFoundException;
import com.hackhero.domainmodule.exceptions.HackathonNotFoundException;
import com.hackhero.domainmodule.exceptions.SubmissionNotFoundException;
import com.hackhero.domainmodule.exceptions.TeamNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final HackathonRepository hackathonRepository;
    private final ChallengeRepository challengeRepository;
    private final TeamRepository teamRepository;
    private final SubmissionMapper submissionMapper;

    public SubmissionResponse createSubmission(CreateSubmissionRequest request) {
        Hackathon hackathon = hackathonRepository.findById(request.hackathonId())
                .orElseThrow(() -> new HackathonNotFoundException("Hackathon not found"));

        if (!hackathon.getStatus().equals(HackathonStatus.STARTED)) {
            throw new IllegalStateException("Hackathon dont start yet");
        }

        Challenge challenge = challengeRepository.findById(request.challengeId())
                .orElseThrow(() -> new ChallengeNotFoundException("Challenge not found"));

        Team team = teamRepository.findById(request.teamId())
                .orElseThrow(() -> new TeamNotFoundException("Team not found"));

        //TODO:

        Submission submission = submissionMapper.toEntity(request);

        submission.setHackathon(hackathon);
        submission.setChallenge(challenge);
        submission.setTeam(team);
        submission.setSubmittedAt(LocalDateTime.now());
        submission.setStatus(SubmissionStatus.SUBMITTED);

        submission = submissionRepository.save(submission);
        return submissionMapper.toResponse(submission);
    }

    public SubmissionResponse updateSubmission(Long id, UpdateSubmissionRequest request) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new SubmissionNotFoundException("Submission not found"));

        if (SubmissionStatus.REVIEWED.equals(submission.getStatus())) {
            throw new IllegalStateException("Submission status is REVIEWED, you can't update it");
        }

        submission.setGitUrl(request.gitUrl());
        submission.setPresentationUrl(request.presentationUrl());
        submission.setDemonstrationUrl(request.demonstrationUrl());
        submission.setDescription(request.description());
        submission.setStatus(SubmissionStatus.UPDATED);
        submission.setScore(request.score());
        submission.setFeedback(request.feedback());

        submission = submissionRepository.save(submission);
        return submissionMapper.toResponse(submission);
    }

    public SubmissionResponse getSubmission(Long id) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new SubmissionNotFoundException("Submission not found"));
        return submissionMapper.toResponse(submission);
    }

    public List<SubmissionResponse> getAllSubmissions() {
        return submissionRepository.findAll()
                .stream()
                .map(submissionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteSubmission(Long id) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new SubmissionNotFoundException("Submission not found"));
        submission.setStatus(SubmissionStatus.CANCELLED);
        submissionRepository.save(submission);
    }

    @Override
    public List<SubmissionResponse> getSubmissionsByHackathon(Long hackathonId) {
        List<Submission> submissions = submissionRepository.findByHackathonId(hackathonId);
        if (submissions.isEmpty()) {
            throw new HackathonNotFoundException("Hackathon or submissions not found");
        }
        return submissions.stream()
                .map(submissionMapper::toResponse)
                .collect(Collectors.toList());
    }
}
