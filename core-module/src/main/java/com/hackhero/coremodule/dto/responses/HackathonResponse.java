package com.hackhero.coremodule.dto.responses;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HackathonResponse {
    private Long id;
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
