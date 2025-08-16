package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.requests.CreateTeamRequest;
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
}
