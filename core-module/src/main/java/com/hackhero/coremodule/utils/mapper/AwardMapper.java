package com.hackhero.coremodule.utils.mapper;

import com.hackhero.coremodule.dto.responses.AwardResponse;
import com.hackhero.domainmodule.entities.Award;
import com.hackhero.domainmodule.entities.users.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AwardMapper {

    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "team.name", target = "teamName")
    @Mapping(source = "hackathon.id", target = "hackathonId")
    @Mapping(source = "hackathon.title", target = "hackathonTitle")
    AwardResponse toResponse(Award award);

    default AwardResponse toResponseWithMembers(Award award) {
        AwardResponse dto = toResponse(award);
        if (award.getTeam() != null && award.getTeam().getParticipants() != null) {
            dto.setTeamMemberIds(
                    award.getTeam().getParticipants()
                            .stream()
                            .map(Participant::getId)
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }
}
