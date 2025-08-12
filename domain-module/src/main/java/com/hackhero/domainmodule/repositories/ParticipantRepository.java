package com.hackhero.domainmodule.repositories;

import com.hackhero.domainmodule.entities.users.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByAuthUserPhoneNumber(String phoneNumber);
}
