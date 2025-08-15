package com.hackhero.coremodule.dto.requests;

import lombok.Data;

@Data
public class CreateJudgeRequest {
    private String phoneNumber;
    private String expertise;
    private String bio;
    private String linkedinUrl;
}
