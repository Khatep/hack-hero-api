package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.requests.CreateOrUpdateOrganizerRequest;
import com.hackhero.coremodule.dto.responses.OrganizerResponse;
import com.hackhero.coremodule.services.OrganizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/organizers")
public class OrganizerController {

    private final OrganizerService organizerService;

    @PostMapping
    public OrganizerResponse createOrganizer(@RequestBody CreateOrUpdateOrganizerRequest request) {
        return organizerService.createOrganizer(request);
    }

    @GetMapping("/{id}")
    public OrganizerResponse getOrganizerById(@PathVariable("id") Long id) {
        return organizerService.getOrganizerById(id);
    }

    @GetMapping
    public List<OrganizerResponse> getAllOrganizers() {
        return organizerService.getAllOrganizers();
    }

    @PutMapping("/{id}")
    public OrganizerResponse updateOrganizer(@PathVariable("id") Long id, @RequestBody CreateOrUpdateOrganizerRequest request) {
        return organizerService.updateOrganizer(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteOrganizer(@PathVariable("id") Long id) {
        organizerService.deleteOrganizer(id);
    }

}
