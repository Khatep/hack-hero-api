package com.hackhero.coremodule.services.impl;

import com.hackhero.coremodule.dto.requests.CreateChallengeRequest;
import com.hackhero.coremodule.dto.responses.ChallengeResponse;
import com.hackhero.coremodule.repositories.ChallengeRepository;
import com.hackhero.coremodule.repositories.HackathonRepository;
import com.hackhero.coremodule.services.ChallengeService;
import com.hackhero.coremodule.utils.mapper.ChallengeMapper;
import com.hackhero.domainmodule.entities.Challenge;
import com.hackhero.domainmodule.entities.Hackathon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final HackathonRepository hackathonRepository;
    private final ChallengeMapper challengeMapper;

    @Override
    public ChallengeResponse createChallenge(CreateChallengeRequest request) {
        Hackathon hackathon = hackathonRepository.findById(request.hackathonId())
                .orElseThrow(() -> new RuntimeException("Hackathon not found"));

        Challenge challenge = challengeMapper.toEntity(request);
        challenge.setHackathon(hackathon);
        hackathon.getChallenges().add(challenge);

        Challenge saved = challengeRepository.save(challenge);

        return challengeMapper.toResponse(saved);
    }
}
