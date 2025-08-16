package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateHackathonRequest;
import com.hackhero.coremodule.dto.responses.HackathonResponse;

public interface HackathonService {
    HackathonResponse createHackathon(CreateHackathonRequest request, Long organizerId);
}
