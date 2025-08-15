package com.hackhero.coremodule.dto.responses;

import lombok.Data;

import java.util.List;

@Data
public class TeamResponse {
    private Long id;
    private String name;
    private String projectName;
    private String description;
    private Long hackathonId;
    private List<Long> participantIds;
}
