package com.hackhero.coremodule.dto.requests;

import lombok.Data;

import java.util.Set;

@Data
public class CreateSponsorRequest {
    private String phoneNumber;
    private String companyName;
    private String logoUrl;
    private String website;
    private Double contributionAmount;
    private Set<Long> hackathonIds;
}
