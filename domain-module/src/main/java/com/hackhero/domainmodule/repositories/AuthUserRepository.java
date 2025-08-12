package com.hackhero.domainmodule.repositories;

import com.hackhero.domainmodule.entities.users.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findByPhoneNumber(String phoneNumber);

}
