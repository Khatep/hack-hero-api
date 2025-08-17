package com.hackhero.coremodule.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AwardResponse {
    private Long teamId;
    private String teamName;
    private List<Long> teamMemberIds;

    private Integer place;

    private Long hackathonId;
    private String hackathonTitle;

    private Integer finalScore;
}
