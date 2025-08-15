package com.hackhero.coremodule.utils.mapper;

import com.hackhero.coremodule.dto.requests.CreateJudgeRequest;
import com.hackhero.coremodule.dto.responses.JudgeResponse;
import com.hackhero.domainmodule.entities.users.Judge;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JudgeMapper {
    Judge toEntity(CreateJudgeRequest request);
    JudgeResponse toResponse(Judge entity);

    List<Judge> toEntityList(List<CreateJudgeRequest> requests);
    List<JudgeResponse> toResponseList(List<Judge> entities);
}
