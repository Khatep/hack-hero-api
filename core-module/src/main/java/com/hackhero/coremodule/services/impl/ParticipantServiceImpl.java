package com.hackhero.coremodule.services.impl;

import com.hackhero.coremodule.dto.requests.CreateParticipantRequest;
import com.hackhero.coremodule.dto.responses.ParticipantResponse;
import com.hackhero.coremodule.repositories.AuthUserRepository;
import com.hackhero.coremodule.repositories.ParticipantRepository;
import com.hackhero.coremodule.services.ParticipantService;
import com.hackhero.coremodule.utils.mapper.ParticipantMapper;
import com.hackhero.domainmodule.entities.users.AuthUser;
import com.hackhero.domainmodule.entities.users.Participant;
import com.hackhero.domainmodule.exceptions.ParticipantNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final AuthUserRepository authUserRepository;
    private final ParticipantMapper participantMapper;

    @Override
    public ParticipantResponse createParticipant(CreateParticipantRequest request) {
        Participant participant = participantMapper.toEntity(request);

        AuthUser authUser = authUserRepository.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Auth user with number - " + request.phoneNumber() + " not found"
                ));
        participant.setAuthUser(authUser);

        ParticipantResponse participantResponse = participantMapper.toResponse(participantRepository.save(participant));
        participantResponse.setPhoneNumber(participant.getAuthUser().getPhoneNumber());

        return participantResponse;
    }

    @Override
    public ParticipantResponse getParticipantById(Long id) {
        return participantRepository.findById(id).map(participantMapper::toResponse)
                .orElseThrow(() -> new ParticipantNotFoundException("Participant with id - " + id + " not found"));
    }

    @Override
    public ParticipantResponse getParticipantByPhoneNumber(String phoneNumber) {
        return participantRepository.findByAuthUserPhoneNumber(phoneNumber).map(participantMapper::toResponse)
                .orElseThrow(() -> new ParticipantNotFoundException("Participant with phone number - " + phoneNumber + " not found"));

    }

    @Override
    public List<ParticipantResponse> getAllParticipants() {
        var participants = participantRepository.findAll();
        return participants.stream()
                .map(participantMapper::toResponse)
                .toList();
    }

    @Override
    public ParticipantResponse updateParticipant(Long id, CreateParticipantRequest request) {
        if (!participantRepository.existsById(id)) {
            throw new ParticipantNotFoundException("Participant with id - " + id + " not found");
        }

        Participant participant = participantMapper.toEntity(request);
        participant.setId(id);
        return participantMapper.toResponse(participantRepository.save(participant));
    }

    @Override
    public void deleteParticipant(Long id) {
        if (!participantRepository.existsById(id)) {
            throw new EntityNotFoundException("Participant not found with id " + id);
        }
        participantRepository.deleteById(id);
    }
}
