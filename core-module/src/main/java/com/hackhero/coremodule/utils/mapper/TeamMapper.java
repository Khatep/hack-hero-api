package com.hackhero.coremodule.utils.mapper;

import com.hackhero.coremodule.dto.requests.CreateTeamRequest;
import com.hackhero.coremodule.dto.responses.TeamResponse;
import com.hackhero.domainmodule.entities.Team;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    Team toEntity(CreateTeamRequest request);
    TeamResponse toResponse(Team entity);

    List<Team> toEntityList(List<CreateTeamRequest> requests);
    List<TeamResponse> toResponseList(List<Team> entities);
}