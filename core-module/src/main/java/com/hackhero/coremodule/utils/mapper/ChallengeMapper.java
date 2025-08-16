package com.hackhero.coremodule.utils.mapper;

import com.hackhero.coremodule.dto.requests.CreateChallengeRequest;
import com.hackhero.coremodule.dto.responses.ChallengeResponse;
import com.hackhero.domainmodule.entities.Challenge;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChallengeMapper {
    Challenge toEntity(CreateChallengeRequest request);
    ChallengeResponse toResponse(Challenge entity);

    List<Challenge> toEntityList(List<CreateChallengeRequest> requests);
    List<ChallengeResponse> toResponseList(List<Challenge> entities);
}
