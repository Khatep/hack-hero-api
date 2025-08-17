package com.hackhero.coremodule.services;

import com.hackhero.domainmodule.entities.Award;

import java.util.List;

public interface AwardService {
    List<Award> calculateWinners(Long hackathonId);
}
