package com.hackhero.authmodule.repositories;

import com.hackhero.domainmodule.entities.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
