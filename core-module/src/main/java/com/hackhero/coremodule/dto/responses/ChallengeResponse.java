package com.hackhero.coremodule.dto.responses;

import lombok.Data;

@Data
public class ChallengeResponse {
    private Long id;
    private String title;
    private String description;
    private Integer maxScore;
}