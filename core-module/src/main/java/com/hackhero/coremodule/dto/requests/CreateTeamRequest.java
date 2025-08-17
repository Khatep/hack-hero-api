package com.hackhero.coremodule.dto.requests;

public record CreateTeamRequest(
     String name,
     String description,
     Long captainId,
     Long hackathonId
) {}
