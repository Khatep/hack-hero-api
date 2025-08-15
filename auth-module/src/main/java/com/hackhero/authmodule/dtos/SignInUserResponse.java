package com.hackhero.authmodule.dtos;

import com.hackhero.coremodule.dto.responses.AbstractUserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInUserResponse<T extends AbstractUserResponse> {
    private JwtResponseDto jwtResponseDto;
    T userResponseDto;
}
