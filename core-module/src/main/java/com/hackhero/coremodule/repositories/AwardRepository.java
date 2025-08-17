package com.hackhero.coremodule.repositories;

import com.hackhero.domainmodule.entities.Award;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AwardRepository extends JpaRepository<Award, Long> {
}
