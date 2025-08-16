package com.hackhero.coremodule.dto.requests;

import java.util.Set;

public record CreateSponsorRequest(
     String phoneNumber,
     String companyName,
     String logoUrl,
     String website,
     Double contributionAmount,
     Set<Long> hackathonIds
) {}
