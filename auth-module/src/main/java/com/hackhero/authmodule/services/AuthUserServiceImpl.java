package com.hackhero.authmodule.services;

import com.hackhero.authmodule.clients.CoreModuleClient;
import com.hackhero.authmodule.dtos.JwtResponseDto;
import com.hackhero.authmodule.dtos.SignInUserResponse;
import com.hackhero.authmodule.repositories.AuthUserRepository;
import com.hackhero.authmodule.repositories.*;
import com.hackhero.coremodule.dto.responses.AbstractUserResponse;
import com.hackhero.coremodule.utils.mapper.GeneralMapper;
import com.hackhero.domainmodule.entities.users.AbstractUser;
import com.hackhero.domainmodule.entities.users.AuthUser;
import com.hackhero.domainmodule.exceptions.*;
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
    private final CoreModuleClient coreModuleClient;
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
    public SignInUserResponse<AbstractUserResponse> signIn(AuthUser authUser, JwtResponseDto jwtResponseDto) {
        AbstractUser userEntity;

        //TODO: Хардкод, при добавлений нового типа клиента придется добавлять код
        switch (authUser.getRole()) {
            case PARTICIPANT -> userEntity = participantRepository.findByAuthUserPhoneNumber(authUser.getPhoneNumber())
                    .orElseThrow(() -> new ParticipantNotFoundException("Participant not found with phone number: " + authUser.getPhoneNumber()));
            case JUDGE -> userEntity = judgeRepository.findByAuthUserPhoneNumber(authUser.getPhoneNumber())
                    .orElseThrow(() -> new JudgeNotFoundException("Judge not found with phone number: " + authUser.getPhoneNumber()));
            case ORGANIZER -> userEntity = organizerRepository.findByAuthUserPhoneNumber(authUser.getPhoneNumber())
                    .orElseThrow(() -> new OrganizerNotFoundException("Organizer not found with phone number: " + authUser.getPhoneNumber()));
            case SPONSOR -> userEntity = sponsorRepository.findByAuthUserPhoneNumber(authUser.getPhoneNumber())
                    .orElseThrow(() -> new SponsorNotFoundException("Sponsor not found with phone number: " + authUser.getPhoneNumber()));
            default -> throw new RuntimeException("Unknown role: " + authUser.getRole());
        }

        AbstractUserResponse userResponseDto = coreModuleClient.mapToResponse(userEntity);

        log.info(userResponseDto.toString());
        return SignInUserResponse.builder()
                .jwtResponseDto(jwtResponseDto)
                .userResponseDto(userResponseDto)
                .build();
    }
}
