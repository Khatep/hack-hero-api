package com.hackhero.coremodule.dto.requests;

import lombok.Data;

@Data
public class CreateSubmissionRequest {
    private Long teamId;
    private Long challengeId;
    private String repoUrl;
    private String demoUrl;
    private String presentationUrl;
    private String description;
}
