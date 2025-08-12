package com.hackhero.authmodule.services;

import com.hackhero.authmodule.dtos.JwtResponseDto;
import com.hackhero.authmodule.dtos.SignInUserResponse;
import com.hackhero.domainmodule.entities.AbstractEntity;
import com.hackhero.domainmodule.entities.users.AuthUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthUserService {

    UserDetailsService userDetailsService() throws UsernameNotFoundException;

    void save(AuthUser authUser);

    void checkIsUserWithPhoneNumberAlreadyExists(String phoneNumber);

    SignInUserResponse<AbstractEntity> signIn(AuthUser user, JwtResponseDto jwtResponseDto);
}
