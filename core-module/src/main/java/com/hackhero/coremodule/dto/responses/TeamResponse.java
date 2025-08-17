package com.hackhero.coremodule.dto.responses;

import com.hackhero.domainmodule.enums.TeamStatus;
import lombok.Data;

import java.util.List;

@Data
public class TeamResponse {
    private Long id;
    private String name;
    private String inviteCode;
    private String description;
    private Integer currentSize;
    private TeamStatus status;
    private Long captainId;
    private Long hackathonId;
    private List<Long> participantIds;
}
