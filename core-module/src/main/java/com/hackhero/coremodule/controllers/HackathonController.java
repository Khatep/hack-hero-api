package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.requests.CreateHackathonRequest;
import com.hackhero.coremodule.dto.responses.HackathonResponse;
import com.hackhero.coremodule.dto.responses.SubmissionResponse;
import com.hackhero.coremodule.dto.responses.TeamResponse;
import com.hackhero.coremodule.services.HackathonService;
import com.hackhero.coremodule.services.SubmissionService;
import com.hackhero.coremodule.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hackathons")
@RequiredArgsConstructor
public class HackathonController {

    private final HackathonService hackathonService;
    private final TeamService teamService;
    private final SubmissionService submissionService;

    @PreAuthorize("hasRole('ORGANIZER')")
    @PostMapping
    public HackathonResponse createHackathon(@RequestBody CreateHackathonRequest request,
                                             @RequestParam("organizerId") Long organizerId) {
        return hackathonService.createHackathon(request, organizerId);
    }

    @GetMapping("/{hackathonId}/teams")
    public List<TeamResponse> getTeamsByHackathon(@PathVariable("hackathonId") Long hackathonId) {
        return teamService.getTeamsByHackathon(hackathonId);
    }

    @GetMapping("/{hackathonId}/submissions")
    public List<SubmissionResponse> getSubmissionsByHackathon(@PathVariable("hackathonId") Long hackathonId) {
        return submissionService.getSubmissionsByHackathon(hackathonId);
    }
}