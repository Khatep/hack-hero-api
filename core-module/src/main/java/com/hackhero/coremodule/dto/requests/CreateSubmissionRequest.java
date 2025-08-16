package com.hackhero.coremodule.dto.requests;

public record CreateSubmissionRequest(
     Long teamId,
     Long challengeId,
     String gitUrl,
     String demonstrationUrl,
     String presentationUrl,
     String description,
     Long hackathonId
) {}
