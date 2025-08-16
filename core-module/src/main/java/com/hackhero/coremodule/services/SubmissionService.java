package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateSubmissionRequest;
import com.hackhero.coremodule.dto.responses.SubmissionResponse;

public interface SubmissionService {
    SubmissionResponse createSubmission(CreateSubmissionRequest request);
    
}
