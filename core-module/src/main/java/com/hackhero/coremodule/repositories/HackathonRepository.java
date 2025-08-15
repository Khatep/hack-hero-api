package com.hackhero.coremodule.repositories;

import com.hackhero.domainmodule.entities.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HackathonRepository extends JpaRepository<Challenge, Long> {
}
