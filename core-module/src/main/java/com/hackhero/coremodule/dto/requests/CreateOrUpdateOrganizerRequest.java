package com.hackhero.coremodule.dto.requests;

public record CreateOrUpdateOrganizerRequest(
        String phoneNumber,
        String organizationName,
        String positionTitle,
        String website,
        String description
) {}
