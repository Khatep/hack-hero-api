package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.responses.HackathonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sponsors")
public class SponsorController {

    @PreAuthorize("hasRole('SPONSOR')")
    @PostMapping("/hackathons/{hackathonId}/sponsor")
    public ResponseEntity<String> sponsorHackathon(
            @PathVariable Long hackathonId,
            @RequestParam String packageName) {
        return ResponseEntity.ok("Sponsor supported hackathon " + hackathonId + " with package " + packageName);
    }

    @PreAuthorize("hasRole('SPONSOR')")
    @GetMapping("/my-sponsorships")
    public ResponseEntity<List<HackathonResponse>> getMySponsorships() {
        return ResponseEntity.ok(List.of());
    }
}