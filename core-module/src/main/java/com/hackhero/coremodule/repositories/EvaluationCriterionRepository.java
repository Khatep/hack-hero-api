package com.hackhero.coremodule.repositories;

import com.hackhero.domainmodule.entities.EvaluationCriterion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationCriterionRepository extends JpaRepository<EvaluationCriterion, Long> {
    List<EvaluationCriterion> findByHackathonId(Long id);
}
