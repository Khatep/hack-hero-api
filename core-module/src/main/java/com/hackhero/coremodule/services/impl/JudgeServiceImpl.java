package com.hackhero.coremodule.services.impl;

import com.hackhero.coremodule.dto.requests.AssignJudgeToHackathonRequest;
import com.hackhero.coremodule.dto.requests.CreateJudgeRequest;
import com.hackhero.coremodule.dto.requests.ScoreSubmissionRequest;
import com.hackhero.coremodule.dto.responses.JudgeResponse;
import com.hackhero.coremodule.dto.responses.SubmissionResponse;
import com.hackhero.coremodule.repositories.AuthUserRepository;
import com.hackhero.coremodule.repositories.HackathonRepository;
import com.hackhero.coremodule.repositories.JudgeRepository;
import com.hackhero.coremodule.repositories.SubmissionRepository;
import com.hackhero.coremodule.services.JudgeService;
import com.hackhero.coremodule.utils.mapper.JudgeMapper;
import com.hackhero.coremodule.utils.mapper.SubmissionMapper;
import com.hackhero.domainmodule.entities.Hackathon;
import com.hackhero.domainmodule.entities.Submission;
import com.hackhero.domainmodule.entities.users.AuthUser;
import com.hackhero.domainmodule.entities.users.Judge;
import com.hackhero.domainmodule.enums.SubmissionStatus;
import com.hackhero.domainmodule.exceptions.HackathonNotFoundException;
import com.hackhero.domainmodule.exceptions.JudgeNotFoundException;
import com.hackhero.domainmodule.exceptions.SubmissionNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JudgeServiceImpl implements JudgeService {

    private final JudgeRepository judgeRepository;
    private final AuthUserRepository authUserRepository;
    private final SubmissionRepository submissionRepository;
    private final HackathonRepository hackathonRepository;
    private final JudgeMapper judgeMapper;
    private final SubmissionMapper submissionMapper;

    @Override
    public JudgeResponse createJudge(CreateJudgeRequest request) {
        Judge judge = judgeMapper.toEntity(request);
        AuthUser authUser = authUserRepository.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new JudgeNotFoundException(
                        "Judge with phone number " + request.phoneNumber() + " not found"
                ));
        judge.setAuthUser(authUser);

        return judgeMapper.toResponse(judgeRepository.save(judge));
    }

    @Override
    public JudgeResponse getJudgeById(Long id) {
        return judgeMapper.toResponse(judgeRepository.findById(id)
                .orElseThrow(() -> new JudgeNotFoundException("Judge with id " + id + " not found")));
    }

    @Override
    public List<JudgeResponse> getAllJudges() {
        return judgeRepository.findAll().stream()
                .map(judgeMapper::toResponse)
                .toList();
    }

    @Override
    public JudgeResponse updateJudge(Long id, CreateJudgeRequest request) {
        if (!judgeRepository.existsById(id)) {
            throw new JudgeNotFoundException("Judge with id " + id + " not found");
        }

        Judge judge = judgeMapper.toEntity(request);
        judge.setId(id);
        return judgeMapper.toResponse(judgeRepository.save(judge));
    }

    @Override
    public void deleteJudge(Long id) {
        if (!judgeRepository.existsById(id)) {
            throw new EntityNotFoundException("Judge not found with id " + id);
        }
        judgeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void assignJudgeToHackathon(AssignJudgeToHackathonRequest request) {
        Judge judge = judgeRepository.findById(request.judgeId())
                .orElseThrow(() -> new JudgeNotFoundException("Judge not found: " + request.judgeId()));
        Hackathon hackathon = hackathonRepository.findById(request.hackathonId())
                .orElseThrow(() -> new HackathonNotFoundException("Hackathon not found: " + request.hackathonId()));

        judge.getHackathons().add(hackathon);
        hackathon.getJudges().add(judge);

        judgeRepository.save(judge);
    }

    @Override
    public List<SubmissionResponse> getAllSubmissions() {
        return submissionRepository.findAll()
                .stream()
                .map(submissionMapper::toResponse)
                .toList();
    }

    @Override
    public void scoreSubmission(Long submissionId, ScoreSubmissionRequest request) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new SubmissionNotFoundException("Submission not found with id " + submissionId));

        if (request.score() < 0 || request.score() > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }

        submission.setScore(request.score());
        submission.setStatus(SubmissionStatus.REVIEWED);
        submission.setFeedback(request.feedback());
        submissionRepository.save(submission);
    }

}
