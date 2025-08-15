package com.hackhero.coremodule.dto.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrganizerResponse extends AbstractUserResponse {
    private Long id;
    private String phoneNumber;
    private String organizationName;
    private String positionTitle;
    private String website;
    private String description;
    private Set<Long> hackathonIds;
}
