package com.hackhero.coremodule.utils.mapper;


import com.hackhero.coremodule.dto.requests.CreateSubmissionRequest;
import com.hackhero.coremodule.dto.responses.SubmissionResponse;
import com.hackhero.domainmodule.entities.Submission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubmissionMapper {
    Submission toEntity(CreateSubmissionRequest request);

    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "challenge.id", target = "challengeId")
    SubmissionResponse toResponse(Submission entity);

    List<Submission> toEntityList(List<CreateSubmissionRequest> requests);
    List<SubmissionResponse> toResponseList(List<Submission> entities);
}