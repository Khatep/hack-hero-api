package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.AssignJudgeToHackathonRequest;
import com.hackhero.coremodule.dto.requests.CreateJudgeRequest;
import com.hackhero.coremodule.dto.responses.JudgeResponse;
import com.hackhero.coremodule.dto.responses.SubmissionResponse;

import java.util.List;

public interface JudgeService {
    JudgeResponse createJudge(CreateJudgeRequest request);

    JudgeResponse getJudgeById(Long id);

    List<JudgeResponse> getAllJudges();

    JudgeResponse updateJudge(Long id, CreateJudgeRequest request);

    void deleteJudge(Long id);

    List<SubmissionResponse> getAllSubmissions();

    void scoreSubmission(Long submissionId, int score);

    void assignJudgeToHackathon(AssignJudgeToHackathonRequest request);
}
