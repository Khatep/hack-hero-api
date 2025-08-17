package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateHackathonRequest;
import com.hackhero.coremodule.dto.responses.HackathonResponse;
import com.hackhero.coremodule.repositories.HackathonRepository;
import com.hackhero.coremodule.repositories.OrganizerRepository;
import com.hackhero.coremodule.services.impl.HackathonServiceImpl;
import com.hackhero.coremodule.utils.mapper.HackathonMapper;
import com.hackhero.domainmodule.entities.Hackathon;
import com.hackhero.domainmodule.entities.users.Organizer;
import com.hackhero.domainmodule.enums.HackathonStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HackathonServiceImplTest {

    @Mock
    private HackathonRepository hackathonRepository;

    @Mock
    private OrganizerRepository organizerRepository;

    @Mock
    private HackathonMapper hackathonMapper;

    @InjectMocks
    private HackathonServiceImpl hackathonService;

    private Organizer organizer;
    private CreateHackathonRequest request;
    private Hackathon hackathon;
    private Hackathon savedHackathon;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        organizer = new Organizer();
        organizer.setId(100L);

        request = new CreateHackathonRequest(
                "Test Hackathon",
                "test-hackathon",
                "Description",
                "rules.pdf",
                "cover.png",
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(20),
                LocalDate.now().plusDays(25),
                true,
                "Online",
                "10000$",
                "AI,ML",
                5
        );

        hackathon = new Hackathon();
        hackathon.setTitle(request.title());
        hackathon.setSlug(request.slug());

        savedHackathon = new Hackathon();
        savedHackathon.setId(1L);
        savedHackathon.setTitle(request.title());
        savedHackathon.setSlug(request.slug());
        savedHackathon.setStatus(HackathonStatus.REG_OPEN);
    }

    @Test
    void testCreateHackathon_success() {
        when(organizerRepository.findById(100L)).thenReturn(Optional.of(organizer));
        when(hackathonMapper.toEntity(request)).thenReturn(hackathon);
        when(hackathonRepository.save(hackathon)).thenReturn(savedHackathon);

        HackathonResponse responseDto = new HackathonResponse();
        responseDto.setId(1L);
        responseDto.setTitle("Test Hackathon");
        responseDto.setSlug("test-hackathon");

        when(hackathonMapper.toResponse(savedHackathon)).thenReturn(responseDto);

        HackathonResponse response = hackathonService.createHackathon(request, 100L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test Hackathon", response.getTitle());
        assertEquals("test-hackathon", response.getSlug());

        assertEquals(HackathonStatus.REG_OPEN, hackathon.getStatus());

        verify(hackathonRepository, times(1)).save(hackathon);
        verify(hackathonMapper, times(1)).toEntity(request);
        verify(hackathonMapper, times(1)).toResponse(savedHackathon);
    }

    @Test
    void testCreateHackathon_organizerNotFound() {
        when(organizerRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                hackathonService.createHackathon(request, 999L));

        verifyNoInteractions(hackathonRepository);
    }
}
