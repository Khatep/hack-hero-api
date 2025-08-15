package com.hackhero.coremodule.dto.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class SponsorResponse extends AbstractUserResponse {
    private Long id;
    private String phoneNumber;
    private String companyName;
    private String logoUrl;
    private String website;
    private Double contributionAmount;
    private Set<Long> hackathonIds;
}
