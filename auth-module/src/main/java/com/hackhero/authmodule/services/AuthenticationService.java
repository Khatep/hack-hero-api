package com.hackhero.authmodule.services;


import com.hackhero.authmodule.dtos.JwtResponseDto;
import com.hackhero.authmodule.dtos.SignInUserRequest;
import com.hackhero.authmodule.dtos.SignInUserResponse;
import com.hackhero.authmodule.dtos.SignUpUserRequest;
import com.hackhero.coremodule.dto.responses.AbstractUserResponse;
import com.hackhero.domainmodule.entities.users.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static com.hackhero.authmodule.utils.PasswordEncoder.encodePassword;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthUserService authUserService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtResponseDto signUpUser(SignUpUserRequest signUpUserRequest) {

        authUserService.checkIsUserWithPhoneNumberAlreadyExists(signUpUserRequest.phoneNumber());

        AuthUser authUser = AuthUser.builder()
                .phoneNumber(signUpUserRequest.phoneNumber())
                .password(encodePassword(signUpUserRequest.password()))
                .role(signUpUserRequest.role())
                .build();

        authUserService.save(authUser);

        return jwtService.generateToken(authUser);
    }

    public SignInUserResponse<AbstractUserResponse> signIn(SignInUserRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.phoneNumber(),
                request.password()
        ));

        AuthUser user = (AuthUser) authUserService
                .userDetailsService()
                .loadUserByUsername(request.phoneNumber());

        return authUserService.signIn(user, jwtService.generateToken(user));
    }
}