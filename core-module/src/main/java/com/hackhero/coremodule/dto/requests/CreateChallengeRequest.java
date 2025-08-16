package com.hackhero.coremodule.dto.requests;

public record CreateChallengeRequest(
        String title,
        String description,
        Integer maxScore,
        Long hackathonId
) {}
