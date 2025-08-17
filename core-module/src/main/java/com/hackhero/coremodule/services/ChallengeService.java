package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateChallengeRequest;
import com.hackhero.coremodule.dto.requests.UpdateChallengeRequest;
import com.hackhero.coremodule.dto.responses.ChallengeResponse;

import java.util.List;

public interface ChallengeService {
    ChallengeResponse createChallenge(CreateChallengeRequest request);
    ChallengeResponse getChallengeById(Long id);
    ChallengeResponse updateChallenge(Long id, UpdateChallengeRequest request);
    void deleteChallenge(Long id);
    List<ChallengeResponse> getChallengesByHackathon(Long hackathonId);
}