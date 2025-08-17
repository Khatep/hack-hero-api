package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.requests.CreateSubmissionRequest;
import com.hackhero.coremodule.dto.requests.UpdateSubmissionRequest;
import com.hackhero.coremodule.dto.responses.SubmissionResponse;
import com.hackhero.coremodule.services.impl.SubmissionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionServiceImpl submissionService;

    @PreAuthorize("hasRole('PARTICIPANT')")
    @PostMapping
    public SubmissionResponse createSubmission(@RequestBody CreateSubmissionRequest request) {
        return submissionService.createSubmission(request);
    }

    @PreAuthorize("hasRole('PARTICIPANT')")
    @PutMapping("/{id}")
    public SubmissionResponse updateSubmission(
            @PathVariable("id") Long id,
            @RequestBody UpdateSubmissionRequest request
    ) {
        return submissionService.updateSubmission(id, request);
    }

    @PreAuthorize("hasAnyRole('PARTICIPANT','JUDGE','ORGANIZER')")
    @GetMapping("/{id}")
    public SubmissionResponse getSubmission(@PathVariable("id") Long id) {
        return submissionService.getSubmission(id);
    }

    @PreAuthorize("hasAnyRole('JUDGE','ORGANIZER')")
    @GetMapping
    public List<SubmissionResponse> getAllSubmissions() {
        return submissionService.getAllSubmissions();
    }

    @PreAuthorize("hasRole('PARTICIPANT')")
    @DeleteMapping("/{id}")
    public void deleteSubmission(@PathVariable("id") Long id) {
        submissionService.deleteSubmission(id);
    }
}