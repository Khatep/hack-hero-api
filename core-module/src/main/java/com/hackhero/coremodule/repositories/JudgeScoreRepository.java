package com.hackhero.coremodule.repositories;

import com.hackhero.domainmodule.entities.JudgeScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JudgeScoreRepository extends JpaRepository<JudgeScore, Long> {
    List<JudgeScore> findBySubmissionId(Long submissionId);
}
