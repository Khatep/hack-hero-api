package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateTeamRequest;
import com.hackhero.coremodule.dto.requests.JoinTeamRequest;
import com.hackhero.coremodule.dto.requests.LeaveFromTeamRequest;
import com.hackhero.coremodule.dto.responses.TeamResponse;

import java.util.List;

public interface TeamService {
    TeamResponse createTeam(CreateTeamRequest request);
    TeamResponse joinTeam(JoinTeamRequest request);
    TeamResponse leaveTeam(LeaveFromTeamRequest request);
    List<TeamResponse> getTeamsByHackathon(Long hackathonId);
}
