package com.hackhero.coremodule.services;

import java.util.Map;

public interface ScoringService {
    void submitScores(Long judgeId, Long submissionId, Map<Long /*criterionId*/, Integer /*score*/> scores, String comment);
}
