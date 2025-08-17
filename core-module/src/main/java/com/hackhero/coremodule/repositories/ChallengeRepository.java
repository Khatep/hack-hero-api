package com.hackhero.coremodule.repositories;

import com.hackhero.domainmodule.entities.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByHackathonId(Long hackathonId);
}
