package com.hackhero.authmodule.controllers;

import com.hackhero.authmodule.dtos.*;
import com.hackhero.authmodule.services.AuthenticationService;
import com.hackhero.coremodule.dto.responses.AbstractUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<JwtInfoDto> signUp(@RequestBody @Valid SignUpUserRequest request) {
        JwtInfoDto jwtInfoDto = authenticationService.signUpUser(request);
        //log.info("new token registered: {}", jwtAuthenticationResponse.getToken());
        return ResponseEntity.status(HttpStatus.OK).body(jwtInfoDto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInUserResponse<AbstractUserResponse>> signIn(@RequestBody @Valid SignInUserRequest request) {
        SignInUserResponse<AbstractUserResponse> jwtResponseDto = authenticationService.signIn(request);
        //log.info("new token registered: {}", jwtAuthenticationResponse.getToken());
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponseDto);
    }
}
