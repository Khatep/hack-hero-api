package com.hackhero.coremodule.dto.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class JudgeResponse extends AbstractUserResponse {
    private Long id;
    private String phoneNumber;
    private String expertise;
    private String bio;
    private String linkedinUrl;
    private Set<Long> hackathonIds;
}
