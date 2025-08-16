package com.hackhero.coremodule.dto.requests;

public record CreateOrganizerRequest(
        String phoneNumber,
        String organizationName,
        String positionTitle,
        String website,
        String description
) {}
