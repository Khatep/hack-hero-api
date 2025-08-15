package com.hackhero.coremodule.repositories;

import com.hackhero.domainmodule.entities.users.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
    Optional<Sponsor> findByAuthUserPhoneNumber(String phoneNumber);
}
