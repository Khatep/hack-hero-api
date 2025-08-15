package com.hackhero.coremodule.dto.requests;

import com.hackhero.domainmodule.enums.GradeStatus;
import lombok.Data;

@Data
public class CreateParticipantRequest {
    private String phoneNumber;
    private String nickname;
    private String bio;
    private String githubUsername;
    private Integer yearsExperience;
    private String email;
    private GradeStatus gradeStatus;
}
