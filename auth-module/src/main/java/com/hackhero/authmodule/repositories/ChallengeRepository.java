package com.hackhero.authmodule.repositories;

import com.hackhero.domainmodule.entities.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
