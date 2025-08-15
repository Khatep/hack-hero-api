package com.hackhero.coremodule.dto.requests;

import lombok.Data;

@Data
public class CreateOrganizerRequest {
    private String phoneNumber;
    private String organizationName;
    private String positionTitle;
    private String website;
    private String description;
}
