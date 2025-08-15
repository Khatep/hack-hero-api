package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.responses.SubmissionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/judges")
public class JudgeController {

    @PreAuthorize("hasRole('JUDGE')")
    @GetMapping("/submissions")
    public ResponseEntity<List<SubmissionResponse>> getAllSubmissions() {
        return ResponseEntity.ok(List.of());
    }

    @PreAuthorize("hasRole('JUDGE')")
    @PostMapping("/submissions/{submissionId}/score")
    public ResponseEntity<String> scoreSubmission(
            @PathVariable Long submissionId,
            @RequestParam int score) {
        return ResponseEntity.ok("Scored submission " + submissionId + " with " + score);
    }
}
