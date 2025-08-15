package com.hackhero.coremodule.repositories;

import com.hackhero.domainmodule.entities.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
