package com.hackhero.coremodule.repositories;

import com.hackhero.domainmodule.entities.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
    List<Hackathon> findByEndAt(LocalDate endAt);
}
