package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateChallengeRequest;
import com.hackhero.coremodule.dto.responses.ChallengeResponse;

public interface ChallengeService {
    ChallengeResponse createChallenge(CreateChallengeRequest request);
}