package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.requests.CreateParticipantRequest;
import com.hackhero.coremodule.dto.requests.CreateSubmissionRequest;
import com.hackhero.coremodule.dto.responses.ParticipantResponse;
import com.hackhero.coremodule.dto.responses.SubmissionResponse;
import com.hackhero.coremodule.services.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping
    public ParticipantResponse createParticipant(@RequestBody CreateParticipantRequest request) {
        return participantService.createParticipant(request);
    }

    @GetMapping("id/{id}")
    public ParticipantResponse getParticipantById(@PathVariable("id") Long id) {
        return participantService.getParticipantById(id);
    }

    @GetMapping("phoneNumber/{phoneNumber}")
    public ParticipantResponse getParticipantByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        return participantService.getParticipantByPhoneNumber(phoneNumber);
    }

    @GetMapping
    public List<ParticipantResponse> getAllParticipants() {
        return participantService.getAllParticipants();
    }

    @PutMapping("/{id}")
    public ParticipantResponse updateParticipant(@PathVariable Long id,
                                                 @RequestBody CreateParticipantRequest request) {
        return participantService.updateParticipant(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
    }

    @PreAuthorize("hasRole('PARTICIPANT')")
    @PostMapping("/teams/join/{teamId}")
    public ResponseEntity<String> joinTeam(@PathVariable Long teamId) {
        // service.joinTeam(...)
        return ResponseEntity.ok("Participant joined team " + teamId);
    }

    @PreAuthorize("hasRole('PARTICIPANT')")
    @PostMapping("/submissions")
    public ResponseEntity<SubmissionResponse> createSubmission(
            @RequestBody CreateSubmissionRequest request) {
        // service.createSubmission(...)
        return ResponseEntity.ok(new SubmissionResponse());
    }

    @PreAuthorize("hasRole('PARTICIPANT')")
    @GetMapping("/my-submissions")
    public ResponseEntity<List<SubmissionResponse>> getMySubmissions() {
        return ResponseEntity.ok(List.of());
    }
}

