package com.hackhero.coremodule.dto.responses;

import lombok.Data;

@Data
public class SubmissionResponse {
    private Long id;
    private Long teamId;
    private Long challengeId;
    private String gitUrl;
    private String demonstrationUrl;
    private String presentationUrl;
    private String description;
}

