package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.requests.CreateTeamRequest;
import com.hackhero.coremodule.dto.requests.JoinTeamRequest;
import com.hackhero.coremodule.dto.requests.LeaveFromTeamRequest;
import com.hackhero.coremodule.dto.responses.TeamResponse;
import com.hackhero.coremodule.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public TeamResponse createTeam(@RequestBody CreateTeamRequest request) {
        return teamService.createTeam(request);
    }

    @PostMapping("/join")
    public TeamResponse joinTeam(@RequestBody JoinTeamRequest request) {
        return teamService.joinTeam(request);
    }

    @DeleteMapping("/leave")
    public TeamResponse leaveTeam(@RequestBody LeaveFromTeamRequest request) {
        return teamService.leaveTeam(request);
    }
}
