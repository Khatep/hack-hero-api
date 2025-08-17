package com.hackhero.coremodule.dto.requests;

public record AssignJudgeToHackathonRequest(
        Long judgeId,
        Long hackathonId
) {}
