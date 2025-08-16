package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.requests.CreateHackathonRequest;
import com.hackhero.coremodule.dto.responses.HackathonResponse;
import com.hackhero.coremodule.services.HackathonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hackathons")
@RequiredArgsConstructor
public class HackathonController {

    private final HackathonService hackathonService;

    @PostMapping
    public HackathonResponse createHackathon(@RequestBody CreateHackathonRequest request,
                                             @RequestParam Long organizerId) {
        return hackathonService.createHackathon(request, organizerId);
    }
}