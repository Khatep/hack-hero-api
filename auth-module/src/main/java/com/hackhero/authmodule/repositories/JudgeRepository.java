package com.hackhero.authmodule.repositories;

import com.hackhero.domainmodule.entities.users.Judge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JudgeRepository extends JpaRepository<Judge, Long> {
    Optional<Judge> findByAuthUserPhoneNumber(String phoneNumber);
}
