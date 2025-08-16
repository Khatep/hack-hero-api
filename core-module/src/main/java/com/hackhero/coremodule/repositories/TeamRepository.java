package com.hackhero.coremodule.repositories;

import com.hackhero.domainmodule.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByInviteCode(String inviteCode);
}
