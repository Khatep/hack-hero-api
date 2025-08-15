package com.hackhero.coremodule.dto.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateHackathonRequest {
    private String title;
    private String slug;
    private String description;
    private String rulesUrl;
    private String coverImageUrl;
    private LocalDate registrationDeadline;
    private LocalDate startAt;
    private LocalDate endAt;
    private Boolean isOnline;
    private String location;
    private String prizePool;
    private String techTags;
    private Integer maxCountOfMembersInTeam;
}