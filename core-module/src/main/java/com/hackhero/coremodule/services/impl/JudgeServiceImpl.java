package com.hackhero.coremodule.services.impl;

import com.hackhero.coremodule.dto.requests.CreateJudgeRequest;
import com.hackhero.coremodule.dto.responses.JudgeResponse;
import com.hackhero.coremodule.dto.responses.SubmissionResponse;
import com.hackhero.coremodule.repositories.AuthUserRepository;
import com.hackhero.coremodule.repositories.JudgeRepository;
import com.hackhero.coremodule.repositories.SubmissionRepository;
import com.hackhero.coremodule.services.JudgeService;
import com.hackhero.coremodule.utils.mapper.JudgeMapper;
import com.hackhero.coremodule.utils.mapper.SubmissionMapper;
import com.hackhero.domainmodule.entities.Submission;
import com.hackhero.domainmodule.entities.users.AuthUser;
import com.hackhero.domainmodule.entities.users.Judge;
import com.hackhero.domainmodule.enums.SubmissionStatus;
import com.hackhero.domainmodule.exceptions.JudgeNotFoundException;
import com.hackhero.domainmodule.exceptions.SubmissionNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JudgeServiceImpl implements JudgeService {

    private final JudgeRepository judgeRepository;
    private final AuthUserRepository authUserRepository;
    private final SubmissionRepository submissionRepository;
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
    public List<SubmissionResponse> getAllSubmissions() {
        return submissionRepository.findAll()
                .stream()
                .map(submissionMapper::toResponse)
                .toList();
    }

    @Override
    public void scoreSubmission(Long submissionId, int score) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new SubmissionNotFoundException("Submission not found with id " + submissionId));

        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }

        submission.setScore(score);
        submission.setStatus(SubmissionStatus.REVIEWED);

        submissionRepository.save(submission);
    }
}
