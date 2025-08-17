package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateParticipantRequest;
import com.hackhero.coremodule.dto.responses.ParticipantResponse;
import com.hackhero.coremodule.repositories.AuthUserRepository;
import com.hackhero.coremodule.repositories.ParticipantRepository;
import com.hackhero.coremodule.services.impl.ParticipantServiceImpl;
import com.hackhero.coremodule.utils.mapper.ParticipantMapper;
import com.hackhero.domainmodule.entities.users.AuthUser;
import com.hackhero.domainmodule.entities.users.Participant;
import com.hackhero.domainmodule.enums.GradeStatus;
import com.hackhero.domainmodule.exceptions.ParticipantNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParticipantServiceImplTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private AuthUserRepository authUserRepository;

    @Mock
    private ParticipantMapper participantMapper;

    @InjectMocks
    private ParticipantServiceImpl participantService;

    private Participant participant;
    private ParticipantResponse response;
    private CreateParticipantRequest request;
    private AuthUser authUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        authUser = new AuthUser();
        authUser.setId(1L);
        authUser.setPhoneNumber("987654321");

        participant = new Participant();
        participant.setId(1L);
        participant.setAuthUser(authUser);
        participant.setNickname("DevNick");
        participant.setEmail("dev@example.com");
        participant.setGradeStatus(GradeStatus.JUNIOR);

        request = new CreateParticipantRequest(
                "987654321",
                "DevNick",
                "Some bio",
                "devGit",
                2,
                "dev@example.com",
                GradeStatus.JUNIOR
        );

        response = new ParticipantResponse();
        response.setId(1L);
        response.setPhoneNumber("987654321");
        response.setNickname("DevNick");
        response.setEmail("dev@example.com");
        response.setGradeStatus(GradeStatus.JUNIOR);
    }

    @Test
    void createParticipant_ShouldReturnResponse() {
        when(participantMapper.toEntity(request)).thenReturn(participant);
        when(authUserRepository.findByPhoneNumber("987654321")).thenReturn(Optional.of(authUser));
        when(participantRepository.save(participant)).thenReturn(participant);
        when(participantMapper.toResponse(participant)).thenReturn(response);

        ParticipantResponse result = participantService.createParticipant(request);

        assertNotNull(result);
        assertEquals("987654321", result.getPhoneNumber());
        verify(participantRepository, times(1)).save(participant);
    }

    @Test
    void createParticipant_ShouldThrow_WhenAuthUserNotFound() {
        when(authUserRepository.findByPhoneNumber("987654321")).thenReturn(Optional.empty());
        when(participantMapper.toEntity(request)).thenReturn(participant);

        assertThrows(EntityNotFoundException.class, () -> participantService.createParticipant(request));
    }

    @Test
    void getParticipantById_ShouldReturnResponse() {
        when(participantRepository.findById(1L)).thenReturn(Optional.of(participant));
        when(participantMapper.toResponse(participant)).thenReturn(response);

        ParticipantResponse result = participantService.getParticipantById(1L);

        assertNotNull(result);
        assertEquals("DevNick", result.getNickname());
    }

    @Test
    void getParticipantById_ShouldThrow_WhenNotFound() {
        when(participantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ParticipantNotFoundException.class, () -> participantService.getParticipantById(1L));
    }

    @Test
    void getParticipantByPhoneNumber_ShouldReturnResponse() {
        when(participantRepository.findByAuthUserPhoneNumber("987654321")).thenReturn(Optional.of(participant));
        when(participantMapper.toResponse(participant)).thenReturn(response);

        ParticipantResponse result = participantService.getParticipantByPhoneNumber("987654321");

        assertNotNull(result);
        assertEquals("dev@example.com", result.getEmail());
    }

    @Test
    void getParticipantByPhoneNumber_ShouldThrow_WhenNotFound() {
        when(participantRepository.findByAuthUserPhoneNumber("987654321")).thenReturn(Optional.empty());

        assertThrows(ParticipantNotFoundException.class, () -> participantService.getParticipantByPhoneNumber("987654321"));
    }

    @Test
    void getAllParticipants_ShouldReturnList() {
        when(participantRepository.findAll()).thenReturn(List.of(participant));
        when(participantMapper.toResponse(participant)).thenReturn(response);

        List<ParticipantResponse> result = participantService.getAllParticipants();

        assertEquals(1, result.size());
        assertEquals("DevNick", result.get(0).getNickname());
    }

    @Test
    void updateParticipant_ShouldReturnUpdatedResponse() {
        when(participantRepository.existsById(1L)).thenReturn(true);
        when(participantMapper.toEntity(request)).thenReturn(participant);
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);
        when(participantMapper.toResponse(participant)).thenReturn(response);

        ParticipantResponse result = participantService.updateParticipant(1L, request);

        assertNotNull(result);
        assertEquals("DevNick", result.getNickname());
    }

    @Test
    void updateParticipant_ShouldThrow_WhenNotFound() {
        when(participantRepository.existsById(1L)).thenReturn(false);

        assertThrows(ParticipantNotFoundException.class, () -> participantService.updateParticipant(1L, request));
    }

    @Test
    void deleteParticipant_ShouldDelete_WhenExists() {
        when(participantRepository.existsById(1L)).thenReturn(true);

        participantService.deleteParticipant(1L);

        verify(participantRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteParticipant_ShouldThrow_WhenNotFound() {
        when(participantRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> participantService.deleteParticipant(1L));
    }
}
