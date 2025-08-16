package com.hackhero.coremodule.dto.requests;

public record UpdateSubmissionRequest (
     String gitUrl,
     String presentationUrl,
     String demonstrationUrl,
     String description,
     Integer score,
     String feedback
) {}