package com.hackhero.coremodule.services.impl;

import com.hackhero.coremodule.dto.requests.CreateHackathonRequest;
import com.hackhero.coremodule.dto.responses.HackathonResponse;
import com.hackhero.coremodule.repositories.HackathonRepository;
import com.hackhero.coremodule.repositories.OrganizerRepository;
import com.hackhero.coremodule.services.HackathonService;
import com.hackhero.coremodule.utils.mapper.HackathonMapper;
import com.hackhero.domainmodule.entities.Hackathon;
import com.hackhero.domainmodule.entities.users.Organizer;
import com.hackhero.domainmodule.enums.HackathonStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HackathonServiceImpl implements HackathonService {

    private final HackathonRepository hackathonRepository;
    private final OrganizerRepository organizerRepository;
    private final HackathonMapper hackathonMapper;

    @Override
    public HackathonResponse createHackathon(CreateHackathonRequest request, Long organizerId) {
        Organizer organizer = organizerRepository.findById(organizerId)
                .orElseThrow(() -> new RuntimeException("Organizer not found"));

        Hackathon hackathon = hackathonMapper.toEntity(request);
        hackathon.setStatus(HackathonStatus.REG_OPEN);

        hackathon.getOrganizers().add(organizer);
        organizer.getHackathons().add(hackathon);

        Hackathon saved = hackathonRepository.save(hackathon);
        return hackathonMapper.toResponse(saved);
    }
}