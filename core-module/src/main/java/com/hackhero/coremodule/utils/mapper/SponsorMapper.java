package com.hackhero.coremodule.utils.mapper;

import com.hackhero.coremodule.dto.requests.CreateSponsorRequest;
import com.hackhero.coremodule.dto.responses.SponsorResponse;
import com.hackhero.domainmodule.entities.users.Sponsor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SponsorMapper {
    Sponsor toEntity(CreateSponsorRequest request);

    @Mapping(source = "authUser.phoneNumber", target = "phoneNumber")
    SponsorResponse toResponse(Sponsor entity);

    List<Sponsor> toEntityList(List<CreateSponsorRequest> requests);
    List<SponsorResponse> toResponseList(List<Sponsor> entities);
}
