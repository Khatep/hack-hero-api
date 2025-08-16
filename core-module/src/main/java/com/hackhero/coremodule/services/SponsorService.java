package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateSponsorRequest;
import com.hackhero.coremodule.dto.responses.SponsorResponse;

import java.util.List;

public interface SponsorService {
    SponsorResponse createSponsor(CreateSponsorRequest request);
    SponsorResponse getSponsorById(Long id);
    List<SponsorResponse> getAllSponsors();
    SponsorResponse updateSponsor(Long id, CreateSponsorRequest request);
    void deleteSponsor(Long id);
}
