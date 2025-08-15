package com.hackhero.authmodule.repositories;

import com.hackhero.domainmodule.entities.users.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    Optional<Organizer> findByAuthUserPhoneNumber(String phoneNumber);
}
