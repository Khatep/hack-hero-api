package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.requests.CreateChallengeRequest;
import com.hackhero.coremodule.dto.requests.UpdateChallengeRequest;
import com.hackhero.coremodule.dto.responses.ChallengeResponse;
import com.hackhero.coremodule.services.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    public ChallengeResponse createChallenge(@RequestBody CreateChallengeRequest request) {
        return challengeService.createChallenge(request);
    }

    @GetMapping("/{id}")
    public ChallengeResponse getChallengeById(@PathVariable("id") Long id) {
        return challengeService.getChallengeById(id);
    }

    @PutMapping("/{id}")
    public ChallengeResponse updateChallenge(@PathVariable("id") Long id,
                                             @RequestBody UpdateChallengeRequest request) {
        return challengeService.updateChallenge(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteChallenge(@PathVariable("id") Long id) {
        challengeService.deleteChallenge(id);
    }

    @GetMapping("/hackathon/{hackathonId}")
    public List<ChallengeResponse> getChallengesByHackathon(@PathVariable("hackathonId") Long hackathonId) {
        return challengeService.getChallengesByHackathon(hackathonId);
    }
}
