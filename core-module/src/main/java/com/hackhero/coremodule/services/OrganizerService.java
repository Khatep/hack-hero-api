package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateOrUpdateOrganizerRequest;
import com.hackhero.coremodule.dto.responses.OrganizerResponse;

import java.util.List;

public interface OrganizerService {
    OrganizerResponse createOrganizer(CreateOrUpdateOrganizerRequest request);
    OrganizerResponse getOrganizerById(Long id);
    List<OrganizerResponse> getAllOrganizers();
    OrganizerResponse updateOrganizer(Long id, CreateOrUpdateOrganizerRequest request);
    void deleteOrganizer(Long id);

}
