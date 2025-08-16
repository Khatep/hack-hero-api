package com.hackhero.coremodule.services.impl;

import com.hackhero.coremodule.dto.requests.CreateOrganizerRequest;
import com.hackhero.coremodule.dto.responses.OrganizerResponse;
import com.hackhero.coremodule.repositories.AuthUserRepository;
import com.hackhero.coremodule.repositories.OrganizerRepository;
import com.hackhero.coremodule.services.OrganizerService;
import com.hackhero.coremodule.utils.mapper.OrganizerMapper;
import com.hackhero.domainmodule.entities.users.AuthUser;
import com.hackhero.domainmodule.entities.users.Organizer;
import com.hackhero.domainmodule.exceptions.OrganizerNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerRepository organizerRepository;
    private final AuthUserRepository authUserRepository;
    private final OrganizerMapper organizerMapper;

    @Override
    public OrganizerResponse createOrganizer(CreateOrganizerRequest request) {
        Organizer organizer = organizerMapper.toEntity(request);

        AuthUser authUser = authUserRepository.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Auth user with number - " + request.phoneNumber() + " not found"
                ));
        organizer.setAuthUser(authUser);

        return organizerMapper.toResponse(organizerRepository.save(organizer));
    }

    @Override
    public OrganizerResponse getOrganizerById(Long id) {
        return organizerRepository.findById(id)
                .map(organizerMapper::toResponse)
                .orElseThrow(() -> new OrganizerNotFoundException("Organizer with id - " + id + " not found"));
    }

    @Override
    public List<OrganizerResponse> getAllOrganizers() {
        return organizerRepository.findAll().stream()
                .map(organizerMapper::toResponse)
                .toList();
    }

    @Override
    public OrganizerResponse updateOrganizer(Long id, CreateOrganizerRequest request) {
        if (!organizerRepository.existsById(id)) {
            throw new OrganizerNotFoundException("Organizer with id - " + id + " not found");
        }

        Organizer organizer = organizerMapper.toEntity(request);
        organizer.setId(id);
        return organizerMapper.toResponse(organizerRepository.save(organizer));
    }

    @Override
    public void deleteOrganizer(Long id) {
        if (!organizerRepository.existsById(id)) {
            throw new EntityNotFoundException("Organizer not found with id " + id);
        }
        organizerRepository.deleteById(id);
    }
}
