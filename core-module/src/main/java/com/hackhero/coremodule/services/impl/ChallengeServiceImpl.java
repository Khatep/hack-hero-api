package com.hackhero.coremodule.services.impl;

import com.hackhero.coremodule.dto.requests.CreateChallengeRequest;
import com.hackhero.coremodule.dto.requests.UpdateChallengeRequest;
import com.hackhero.coremodule.dto.responses.ChallengeResponse;
import com.hackhero.coremodule.repositories.ChallengeRepository;
import com.hackhero.coremodule.repositories.HackathonRepository;
import com.hackhero.coremodule.services.ChallengeService;
import com.hackhero.coremodule.utils.mapper.ChallengeMapper;
import com.hackhero.domainmodule.entities.Challenge;
import com.hackhero.domainmodule.entities.Hackathon;
import com.hackhero.domainmodule.exceptions.ChallengeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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

    @Override
    public ChallengeResponse getChallengeById(Long id) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new ChallengeNotFoundException("Challenge not found"));
        return challengeMapper.toResponse(challenge);
    }

    @Override
    public ChallengeResponse updateChallenge(Long id, UpdateChallengeRequest request) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new ChallengeNotFoundException("Challenge not found"));

        challenge.setTitle(request.title());
        challenge.setDescription(request.description());
        challenge.setMaxScore(request.maxScore());

        return challengeMapper.toResponse(challengeRepository.save(challenge));
    }

    @Override
    public void deleteChallenge(Long id) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new ChallengeNotFoundException("Challenge not found"));
        challengeRepository.delete(challenge);
    }

    @Override
    public List<ChallengeResponse> getChallengesByHackathon(Long hackathonId) {
        List<Challenge> challenges = challengeRepository.findByHackathonId(hackathonId);
        return challengeMapper.toResponseList(challenges);
    }
    @Override
    public List<ChallengeResponse> getChallengesByHackathonId(long hackathonId) {
        List<Challenge> challenges = challengeRepository.findByHackathonId(hackathonId);

        if (challenges == null || challenges.isEmpty()) {
            return Collections.emptyList();
        }

        return challengeMapper.toResponseList(challenges);
    }

}
