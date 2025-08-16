package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateOrganizerRequest;
import com.hackhero.coremodule.dto.responses.OrganizerResponse;

import java.util.List;

public interface OrganizerService {
    OrganizerResponse createOrganizer(CreateOrganizerRequest request);
    OrganizerResponse getOrganizerById(Long id);
    List<OrganizerResponse> getAllOrganizers();
    OrganizerResponse updateOrganizer(Long id, CreateOrganizerRequest request);
    void deleteOrganizer(Long id);

}
