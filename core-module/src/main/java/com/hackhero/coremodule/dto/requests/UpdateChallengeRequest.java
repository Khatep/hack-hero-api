package com.hackhero.coremodule.dto.requests;

public record UpdateChallengeRequest(
        String title,
        String description,
        Integer maxScore
) {}
