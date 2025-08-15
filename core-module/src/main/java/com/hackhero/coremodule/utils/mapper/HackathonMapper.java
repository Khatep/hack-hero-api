package com.hackhero.coremodule.utils.mapper;

import com.hackhero.coremodule.dto.requests.CreateHackathonRequest;
import com.hackhero.coremodule.dto.responses.HackathonResponse;
import com.hackhero.domainmodule.entities.Hackathon;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HackathonMapper {
    Hackathon toEntity(CreateHackathonRequest request);
    HackathonResponse toResponse(Hackathon entity);

    List<Hackathon> toEntityList(List<CreateHackathonRequest> requests);
    List<HackathonResponse> toResponseList(List<Hackathon> entities);
}

