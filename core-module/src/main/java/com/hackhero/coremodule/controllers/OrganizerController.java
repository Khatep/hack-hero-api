package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.requests.CreateHackathonRequest;
import com.hackhero.coremodule.dto.requests.CreateOrganizerRequest;
import com.hackhero.coremodule.dto.requests.CreateTeamRequest;
import com.hackhero.coremodule.dto.responses.HackathonResponse;
import com.hackhero.coremodule.dto.responses.OrganizerResponse;
import com.hackhero.coremodule.dto.responses.SubmissionResponse;
import com.hackhero.coremodule.dto.responses.TeamResponse;
import com.hackhero.coremodule.services.OrganizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/organizers")
public class OrganizerController {

    private final OrganizerService organizerService;

    @PostMapping
    public OrganizerResponse createOrganizer(@RequestBody CreateOrganizerRequest request) {
        return organizerService.createOrganizer(request);
    }

    @GetMapping("/{id}")
    public OrganizerResponse getOrganizerById(@PathVariable Long id) {
        return organizerService.getOrganizerById(id);
    }

    @GetMapping
    public List<OrganizerResponse> getAllOrganizers() {
        return organizerService.getAllOrganizers();
    }

    @PutMapping("/{id}")
    public OrganizerResponse updateOrganizer(@PathVariable Long id, @RequestBody CreateOrganizerRequest request) {
        return organizerService.updateOrganizer(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteOrganizer(@PathVariable Long id) {
        organizerService.deleteOrganizer(id);
    }

    @PostMapping("/hackathons")
    public ResponseEntity<HackathonResponse> createHackathon(@RequestBody CreateHackathonRequest request) {
        return ResponseEntity.ok(new HackathonResponse());
    }

    @PostMapping("/teams")
    public ResponseEntity<TeamResponse> createTeam(@RequestBody CreateTeamRequest request) {
        return ResponseEntity.ok(new TeamResponse());
    }

    @GetMapping("/submissions")
    public ResponseEntity<List<SubmissionResponse>> getAllSubmissions() {
        return ResponseEntity.ok(List.of());
    }
}
