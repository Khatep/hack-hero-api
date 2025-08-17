package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.responses.AwardResponse;
import com.hackhero.domainmodule.entities.Award;

import java.util.List;

public interface AwardService {
    List<AwardResponse> calculateWinners(Long hackathonId);
}
