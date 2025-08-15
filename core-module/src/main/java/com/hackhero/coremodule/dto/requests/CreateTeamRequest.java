package com.hackhero.coremodule.dto.requests;

import lombok.Data;

import java.util.List;

@Data
public class CreateTeamRequest {
    private String name;
    private String projectName;
    private String description;
    private Long hackathonId;
    private List<Long> participantIds;
}
