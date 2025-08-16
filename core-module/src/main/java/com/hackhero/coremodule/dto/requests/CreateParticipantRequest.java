package com.hackhero.coremodule.dto.requests;

import com.hackhero.domainmodule.enums.GradeStatus;

public record CreateParticipantRequest(
        String phoneNumber,
        String nickname,
        String bio,
        String githubUsername,
        Integer yearsExperience,
        String email,
        GradeStatus gradeStatus
) {}
