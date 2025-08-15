package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateParticipantRequest;
import com.hackhero.coremodule.dto.responses.ParticipantResponse;

import java.util.List;

public interface ParticipantService {
    ParticipantResponse createParticipant(CreateParticipantRequest request);
    ParticipantResponse getParticipantById(Long id);
    ParticipantResponse getParticipantByPhoneNumber(String phoneNumber);
    List<ParticipantResponse> getAllParticipants();
    ParticipantResponse updateParticipant(Long id, CreateParticipantRequest request);
    void deleteParticipant(Long id);
}
    
