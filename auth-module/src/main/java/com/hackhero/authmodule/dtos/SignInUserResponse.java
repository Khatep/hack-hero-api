package com.hackhero.authmodule.dtos;

import com.hackhero.domainmodule.entities.AbstractEntity;
import com.hackhero.domainmodule.entities.users.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInUserResponse<T extends AbstractEntity> {
    private JwtResponseDto jwtResponseDto;
    T user;
}
