package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.requests.CreateChallengeRequest;
import com.hackhero.coremodule.dto.responses.ChallengeResponse;
import com.hackhero.coremodule.services.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    public ChallengeResponse createChallenge(@RequestBody CreateChallengeRequest request) {
        return challengeService.createChallenge(request);
    }
}
