package com.hackhero.coremodule.dto.requests;

public record ScoreSubmissionRequest(
    int score,
    String feedback
) {}

