package com.hackhero.coremodule.utils.mapper;

import com.hackhero.coremodule.dto.requests.CreateOrUpdateOrganizerRequest;
import com.hackhero.coremodule.dto.responses.OrganizerResponse;
import com.hackhero.domainmodule.entities.users.Organizer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganizerMapper {
    Organizer toEntity(CreateOrUpdateOrganizerRequest request);

    @Mapping(source = "authUser.phoneNumber", target = "phoneNumber")
    OrganizerResponse toResponse(Organizer entity);

    List<Organizer> toEntityList(List<CreateOrUpdateOrganizerRequest> requests);
    List<OrganizerResponse> toResponseList(List<Organizer> entities);
}
