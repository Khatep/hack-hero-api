package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.common.TeamRanking;
import com.hackhero.domainmodule.entities.Submission;

import java.util.List;

public interface ResultAggregationService {
    void recomputeSubmissionScore(Long submissionId);
    List<Submission> getChallengeLeaderboard(Long hackathonId, Long challengeId, int limit);
    List<TeamRanking> getOverallLeaderboard(Long hackathonId, int limit);
}