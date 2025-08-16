package com.hackhero.coremodule.dto.requests;

public record JoinTeamRequest(
    Long participantId,
    String inviteCode
) {}