package com.hackhero.authmodule.services;

import com.hackhero.authmodule.dtos.JwtResponseDto;
import com.hackhero.authmodule.dtos.SignInUserResponse;
import com.hackhero.domainmodule.entities.AbstractEntity;
import com.hackhero.domainmodule.entities.users.AuthUser;
import com.hackhero.domainmodule.exceptions.*;
import com.hackhero.domainmodule.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthUserServiceImpl implements AuthUserService {
    private final AuthUserRepository authUserRepository;

    //TODO hardcode
    private final JudgeRepository judgeRepository;
    private final OrganizerRepository organizerRepository;
    private final ParticipantRepository participantRepository;
    private final SponsorRepository sponsorRepository;

    @Override
    public UserDetailsService userDetailsService() throws UsernameNotFoundException {
        return this::getByPhoneNumber;
    }

    private AuthUser getByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        return authUserRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with phone number: " + phoneNumber));
    }

    @Override
    public void save(AuthUser authUser) {
        authUserRepository.save(authUser);
    }

    @Override
    public void checkIsUserWithPhoneNumberAlreadyExists(String phoneNumber) {
        Optional<AuthUser> authUser = authUserRepository.findByPhoneNumber(phoneNumber);

        if (authUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists with phone number: " + phoneNumber);
        }
    }

    @Override
    public SignInUserResponse<AbstractEntity> signIn(AuthUser authUser, JwtResponseDto jwtResponseDto) {
        AbstractEntity clientData;
        switch (authUser.getRole()) {
            case PARTICIPANT -> clientData = participantRepository.findByAuthUserPhoneNumber(authUser.getPhoneNumber())
                    .orElseThrow(() -> new ParticipantNotFoundException("Participant not found with phone number: " + authUser.getPhoneNumber()));
            case JUDGE -> clientData = judgeRepository.findByAuthUserPhoneNumber(authUser.getPhoneNumber())
                    .orElseThrow(() -> new JudgeNotFoundException("Judge not found with phone number: " + authUser.getPhoneNumber()));
            case ORGANIZER -> clientData = organizerRepository.findByAuthUserPhoneNumber(authUser.getPhoneNumber())
                    .orElseThrow(() -> new OrganizerNotFoundException("Organizer not found with phone number: " + authUser.getPhoneNumber()));
            case SPONSOR -> clientData = sponsorRepository.findByAuthUserPhoneNumber(authUser.getPhoneNumber())
                    .orElseThrow(() -> new SponsorNotFoundException("Sponsor not found with phone number: " + authUser.getPhoneNumber()));
            default -> throw new RuntimeException("Unknown role: " + authUser.getRole());
        }

        log.info(clientData.toString());
        return SignInUserResponse.builder()
                .jwtResponseDto(jwtResponseDto)
                .user(clientData)
                .build();
    }
}
