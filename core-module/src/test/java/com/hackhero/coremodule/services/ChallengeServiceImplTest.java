package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateChallengeRequest;
import com.hackhero.coremodule.dto.requests.UpdateChallengeRequest;
import com.hackhero.coremodule.dto.responses.ChallengeResponse;
import com.hackhero.coremodule.repositories.ChallengeRepository;
import com.hackhero.coremodule.repositories.HackathonRepository;
import com.hackhero.coremodule.services.impl.ChallengeServiceImpl;
import com.hackhero.coremodule.utils.mapper.ChallengeMapper;
import com.hackhero.domainmodule.entities.Challenge;
import com.hackhero.domainmodule.entities.Hackathon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChallengeServiceImplTest {

    @Mock
    private ChallengeRepository challengeRepository;

    @Mock
    private HackathonRepository hackathonRepository;

    @Mock
    private ChallengeMapper challengeMapper;

    @InjectMocks
    private ChallengeServiceImpl challengeService;

    private Challenge challenge;
    private Hackathon hackathon;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        hackathon = new Hackathon();
        hackathon.setId(1L);

        challenge = new Challenge();
        challenge.setId(100L);
        challenge.setTitle("Test Challenge");
        challenge.setHackathon(hackathon);
    }

    @Test
    void testCreateChallenge() {
        CreateChallengeRequest request = new CreateChallengeRequest("Title", "Desc", 100, 1L);

        when(hackathonRepository.findById(1L)).thenReturn(Optional.of(hackathon));
        when(challengeMapper.toEntity(request)).thenReturn(challenge);
        when(challengeRepository.save(any(Challenge.class))).thenReturn(challenge);
        when(challengeMapper.toResponse(challenge)).thenReturn(new ChallengeResponse(100L, "Title", "Desc", 100));

        ChallengeResponse response = challengeService.createChallenge(request);

        assertNotNull(response);
        assertEquals("Title", response.getTitle());
        verify(challengeRepository, times(1)).save(any(Challenge.class));
    }

    @Test
    void testGetChallengeById() {
        when(challengeRepository.findById(100L)).thenReturn(Optional.of(challenge));
        when(challengeMapper.toResponse(challenge)).thenReturn(new ChallengeResponse(100L, "Test Challenge", "Desc", 50));

        ChallengeResponse response = challengeService.getChallengeById(100L);

        assertNotNull(response);
        assertEquals(100L, response.getId());
        verify(challengeRepository).findById(100L);
    }

    @Test
    void testUpdateChallenge() {
        UpdateChallengeRequest request = new UpdateChallengeRequest("Updated", "New Desc", 150);

        when(challengeRepository.findById(100L)).thenReturn(Optional.of(challenge));
        when(hackathonRepository.findById(1L)).thenReturn(Optional.of(hackathon));
        when(challengeRepository.save(any(Challenge.class))).thenReturn(challenge);
        when(challengeMapper.toResponse(challenge)).thenReturn(new ChallengeResponse(100L, "Updated", "New Desc", 150));

        ChallengeResponse response = challengeService.updateChallenge(100L, request);

        assertNotNull(response);
        assertEquals("Updated", response.getTitle());
        assertEquals(150, response.getMaxScore());
    }

    @Test
    void testDeleteChallenge() {
        when(challengeRepository.findById(100L)).thenReturn(Optional.of(challenge));
        doNothing().when(challengeRepository).delete(challenge);

        challengeService.deleteChallenge(100L);

        verify(challengeRepository, times(1)).delete(challenge);
    }

    @Test
    void testGetChallengesByHackathonId() {
        when(challengeRepository.findByHackathonId(1L)).thenReturn(List.of(challenge));
        when(challengeMapper.toResponseList(List.of(challenge)))
                .thenReturn(List.of(new ChallengeResponse(100L, "Test Challenge", "Desc", 50)));

        List<ChallengeResponse> responses = challengeService.getChallengesByHackathonId(1L);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Test Challenge", responses.get(0).getTitle());
    }
}
