package com.hackhero.coremodule.dto.common;

import lombok.Builder;

import java.util.Map;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRanking {
    private Long teamId;
    private String teamName;
    private Integer totalScore;
    private Map<Long, Integer> bestByChallenge; // challengeId -> bestScore
    private Integer rank;
}
