package com.hackhero.coremodule.dto.requests;

public record LeaveFromTeamRequest(
        Long participantId,
        Long hackathonId,
        Long teamId
) {}
