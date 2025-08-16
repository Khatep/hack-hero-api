package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateTeamRequest;
import com.hackhero.coremodule.dto.requests.JoinTeamRequest;
import com.hackhero.coremodule.dto.responses.TeamResponse;

public interface TeamService {
    TeamResponse createTeam(CreateTeamRequest request);
    TeamResponse joinTeam(JoinTeamRequest request);
    TeamResponse leaveTeam(Long participantId, Long teamId);
}
