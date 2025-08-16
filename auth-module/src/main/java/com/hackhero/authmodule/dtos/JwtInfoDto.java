package com.hackhero.authmodule.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtInfoDto {
    private String token;
    private Date createdAt;
    private Date expiresAt;
}
