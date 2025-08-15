package com.hackhero.coremodule.utils.mapper;

import com.hackhero.coremodule.dto.requests.CreateOrganizerRequest;
import com.hackhero.coremodule.dto.responses.OrganizerResponse;
import com.hackhero.domainmodule.entities.users.Organizer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganizerMapper {
    Organizer toEntity(CreateOrganizerRequest request);
    OrganizerResponse toResponse(Organizer entity);

    List<Organizer> toEntityList(List<CreateOrganizerRequest> requests);
    List<OrganizerResponse> toResponseList(List<Organizer> entities);
}
