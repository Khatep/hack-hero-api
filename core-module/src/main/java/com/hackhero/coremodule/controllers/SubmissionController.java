package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.requests.CreateSubmissionRequest;
import com.hackhero.coremodule.dto.requests.UpdateSubmissionRequest;
import com.hackhero.coremodule.dto.responses.SubmissionResponse;
import com.hackhero.coremodule.services.impl.SubmissionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionServiceImpl submissionService;

    @PreAuthorize("hasRole('PARTICIPANT')")
    @PostMapping
    public ResponseEntity<SubmissionResponse> createSubmission(@RequestBody CreateSubmissionRequest request) {
        SubmissionResponse response = submissionService.createSubmission(request);
        return ResponseEntity.created(URI.create("/api/v1/submissions/" + response.getId()))
                .body(response);
    }

    @PreAuthorize("hasRole('PARTICIPANT')")
    @PutMapping("/{id}")
    public ResponseEntity<SubmissionResponse> updateSubmission(
            @PathVariable Long id,
            @RequestBody UpdateSubmissionRequest request
    ) {
        return ResponseEntity.ok(submissionService.updateSubmission(id, request));
    }

    @PreAuthorize("hasAnyRole('PARTICIPANT','JUDGE','ORGANIZER')")
    @GetMapping("/{id}")
    public ResponseEntity<SubmissionResponse> getSubmission(@PathVariable Long id) {
        return ResponseEntity.ok(submissionService.getSubmission(id));
    }

    @PreAuthorize("hasAnyRole('JUDGE','ORGANIZER')")
    @GetMapping
    public ResponseEntity<List<SubmissionResponse>> getAllSubmissions() {
        return ResponseEntity.ok(submissionService.getAllSubmissions());
    }

    @PreAuthorize("hasRole('PARTICIPANT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long id) {
        submissionService.deleteSubmission(id);
        return ResponseEntity.noContent().build();
    }
}