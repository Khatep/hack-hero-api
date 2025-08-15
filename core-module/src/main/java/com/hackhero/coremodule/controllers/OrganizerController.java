package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.requests.CreateHackathonRequest;
import com.hackhero.coremodule.dto.requests.CreateTeamRequest;
import com.hackhero.coremodule.dto.responses.HackathonResponse;
import com.hackhero.coremodule.dto.responses.SubmissionResponse;
import com.hackhero.coremodule.dto.responses.TeamResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organizers")
public class OrganizerController {

    @PreAuthorize("hasRole('ORGANIZER')")
    @PostMapping("/hackathons")
    public ResponseEntity<HackathonResponse> createHackathon(
            @RequestBody CreateHackathonRequest request) {
        return ResponseEntity.ok(new HackathonResponse());
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @PostMapping("/teams")
    public ResponseEntity<TeamResponse> createTeam(
            @RequestBody CreateTeamRequest request) {
        return ResponseEntity.ok(new TeamResponse());
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @GetMapping("/submissions")
    public ResponseEntity<List<SubmissionResponse>> getAllSubmissions() {
        return ResponseEntity.ok(List.of());
    }
}
