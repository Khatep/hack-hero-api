package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateSubmissionRequest;
import com.hackhero.coremodule.dto.requests.UpdateSubmissionRequest;
import com.hackhero.coremodule.dto.responses.SubmissionResponse;

import java.util.List;

public interface SubmissionService {
    SubmissionResponse createSubmission(CreateSubmissionRequest request);
    SubmissionResponse updateSubmission(Long id, UpdateSubmissionRequest request);
    SubmissionResponse getSubmission(Long id);
    List<SubmissionResponse> getAllSubmissions();
    void deleteSubmission(Long id);
    List<SubmissionResponse> getSubmissionsByHackathon(Long hackathonId);
}
