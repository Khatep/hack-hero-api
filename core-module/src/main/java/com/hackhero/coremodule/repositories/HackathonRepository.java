package com.hackhero.coremodule.repositories;

import com.hackhero.domainmodule.entities.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
}
