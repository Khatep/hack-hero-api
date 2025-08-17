package com.hackhero.coremodule.dto.requests;

import java.time.LocalDate;

public record CreateHackathonRequest(
        String title,
        String slug,
        String description,
        String rulesUrl,
        String coverImageUrl,
        LocalDate registrationDeadline,
        LocalDate startAt,
        LocalDate endAt,
        Boolean isOnline,
        String location,
        String prizePool,
        String techTags,
        Integer maxCountOfMembersInTeam
) {}