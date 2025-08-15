package com.hackhero.coremodule.utils.mapper;

import com.hackhero.coremodule.dto.requests.CreateParticipantRequest;
import com.hackhero.coremodule.dto.responses.ParticipantResponse;
import com.hackhero.domainmodule.entities.users.Participant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {
    Participant toEntity(CreateParticipantRequest request);
    ParticipantResponse toResponse(Participant entity);

    List<Participant> toEntityList(List<CreateParticipantRequest> requests);
    List<ParticipantResponse> toResponseList(List<Participant> entities);
}