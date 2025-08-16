package com.hackhero.coremodule.dto.requests;

public record CreateJudgeRequest(
        String phoneNumber,
        String expertise,
        String bio,
        String linkedinUrl
) {}
