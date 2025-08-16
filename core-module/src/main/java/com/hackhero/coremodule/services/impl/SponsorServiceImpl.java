package com.hackhero.coremodule.services.impl;

import com.hackhero.coremodule.dto.requests.CreateSponsorRequest;
import com.hackhero.coremodule.dto.responses.SponsorResponse;
import com.hackhero.coremodule.repositories.AuthUserRepository;
import com.hackhero.coremodule.repositories.SponsorRepository;
import com.hackhero.coremodule.services.SponsorService;
import com.hackhero.coremodule.utils.mapper.SponsorMapper;
import com.hackhero.domainmodule.entities.users.AuthUser;
import com.hackhero.domainmodule.entities.users.Sponsor;
import com.hackhero.domainmodule.exceptions.SponsorNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SponsorServiceImpl implements SponsorService {

    private final SponsorRepository sponsorRepository;
    private final AuthUserRepository authUserRepository;
    private final SponsorMapper sponsorMapper;

    @Override
    public SponsorResponse createSponsor(CreateSponsorRequest request) {
        Sponsor sponsor = sponsorMapper.toEntity(request);

        AuthUser authUser = authUserRepository.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Auth user with number - " + request.phoneNumber() + " not found"
                ));
        sponsor.setAuthUser(authUser);

        return sponsorMapper.toResponse(sponsorRepository.save(sponsor));
    }

    @Override
    public SponsorResponse getSponsorById(Long id) {
        return sponsorRepository.findById(id)
                .map(sponsorMapper::toResponse)
                .orElseThrow(() -> new SponsorNotFoundException("Sponsor with id - " + id + " not found"));
    }

    @Override
    public List<SponsorResponse> getAllSponsors() {
        var sponsors = sponsorRepository.findAll();
        return sponsors.stream()
                .map(sponsorMapper::toResponse)
                .toList();
    }

    @Override
    public SponsorResponse updateSponsor(Long id, CreateSponsorRequest request) {
        if (!sponsorRepository.existsById(id)) {
            throw new SponsorNotFoundException("Sponsor with id - " + id + " not found");
        }

        Sponsor sponsor = sponsorMapper.toEntity(request);
        sponsor.setId(id);
        return sponsorMapper.toResponse(sponsorRepository.save(sponsor));
    }

    @Override
    public void deleteSponsor(Long id) {
        if (!sponsorRepository.existsById(id)) {
            throw new EntityNotFoundException("Sponsor not found with id " + id);
        }
        sponsorRepository.deleteById(id);
    }
}
