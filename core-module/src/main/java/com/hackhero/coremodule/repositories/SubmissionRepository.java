package com.hackhero.coremodule.repositories;

import com.hackhero.domainmodule.entities.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    @Query("""
           select s
           from Submission s
           where s.hackathon.id = :hackathonId
             and s.challenge.id = :challengeId
             and s.score is not null
           order by s.score desc, s.submittedAt asc
           """)
    List<Submission> findTopByHackathonAndChallengeOrdered(Long hackathonId, Long challengeId);

    @Query("""
           select s
           from Submission s
           where s.hackathon.id = :hackathonId
             and s.score is not null
           """)
    List<Submission> findAllWithScoreByHackathon(Long hackathonId);

    List<Submission> findByHackathonId(Long hackathonId);
}
