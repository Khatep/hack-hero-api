package com.hackhero.coremodule.utils.mapper;

import com.hackhero.coremodule.dto.requests.CreateTeamRequest;
import com.hackhero.coremodule.dto.responses.TeamResponse;
import com.hackhero.domainmodule.entities.AbstractEntity;
import com.hackhero.domainmodule.entities.Hackathon;
import com.hackhero.domainmodule.entities.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    Team toEntity(CreateTeamRequest request);

    @Mapping(source = "captain.id", target = "captainId")
    @Mapping(source = "hackathons", target = "hackathonId", qualifiedByName = "mapFirstHackathonId")
    @Mapping(source = "participants", target = "participantIds", qualifiedByName = "mapParticipantIds")
    TeamResponse toResponse(Team entity);

    List<Team> toEntityList(List<CreateTeamRequest> requests);

    List<TeamResponse> toResponseList(List<Team> entities);

    @Named("mapFirstHackathonId")
    default Long mapFirstHackathonId(Set<Hackathon> hackathons) {
        return hackathons == null || hackathons.isEmpty()
                ? null
                : hackathons.iterator().next().getId();
    }

    @Named("mapParticipantIds")
    default List<Long> mapParticipantIds(Set<com.hackhero.domainmodule.entities.users.Participant> participants) {
        return participants == null
                ? List.of()
                : participants.stream().map(AbstractEntity::getId).collect(Collectors.toList());
    }
}