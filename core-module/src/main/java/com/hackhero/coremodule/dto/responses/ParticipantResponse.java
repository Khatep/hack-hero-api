package com.hackhero.coremodule.dto.responses;

import com.hackhero.domainmodule.enums.GradeStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ParticipantResponse extends AbstractUserResponse {
    private Long id;
    private String phoneNumber;
    private String nickname;
    private String bio;
    private String githubUsername;
    private Integer yearsExperience;
    private String email;
    private GradeStatus gradeStatus;

//    private String skills;
//    private String resumeUrl;
}