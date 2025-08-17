package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateSponsorRequest;
import com.hackhero.coremodule.dto.responses.SponsorResponse;
import com.hackhero.coremodule.repositories.AuthUserRepository;
import com.hackhero.coremodule.repositories.SponsorRepository;
import com.hackhero.coremodule.services.impl.SponsorServiceImpl;
import com.hackhero.coremodule.utils.mapper.SponsorMapper;
import com.hackhero.domainmodule.entities.users.AuthUser;
import com.hackhero.domainmodule.entities.users.Sponsor;
import com.hackhero.domainmodule.exceptions.SponsorNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SponsorServiceImplTest {

    @Mock
    private SponsorRepository sponsorRepository;

    @Mock
    private AuthUserRepository authUserRepository;

    @Mock
    private SponsorMapper sponsorMapper;

    @InjectMocks
    private SponsorServiceImpl sponsorService;

    private Sponsor sponsor;
    private SponsorResponse response;
    private CreateSponsorRequest request;
    private AuthUser authUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        authUser = new AuthUser();
        authUser.setId(1L);
        authUser.setPhoneNumber("111222333");

        sponsor = new Sponsor();
        sponsor.setId(1L);
        sponsor.setAuthUser(authUser);
        sponsor.setCompanyName("TechCorp");
        sponsor.setContributionAmount(10000.0);

        request = new CreateSponsorRequest(
                "111222333",
                "TechCorp",
                "logo.png",
                "https://techcorp.com",
                10000.0,
                Set.of(1L, 2L)
        );

        response = new SponsorResponse();
        response.setId(1L);
        response.setPhoneNumber("111222333");
        response.setCompanyName("TechCorp");
        response.setContributionAmount(10000.0);
        response.setHackathonIds(Set.of(1L, 2L));
    }

    @Test
    void createSponsor_ShouldReturnResponse() {
        when(sponsorMapper.toEntity(request)).thenReturn(sponsor);
        when(authUserRepository.findByPhoneNumber("111222333")).thenReturn(Optional.of(authUser));
        when(sponsorRepository.save(sponsor)).thenReturn(sponsor);
        when(sponsorMapper.toResponse(sponsor)).thenReturn(response);

        SponsorResponse result = sponsorService.createSponsor(request);

        assertNotNull(result);
        assertEquals("TechCorp", result.getCompanyName());
        verify(sponsorRepository, times(1)).save(sponsor);
    }

    @Test
    void createSponsor_ShouldThrow_WhenAuthUserNotFound() {
        when(authUserRepository.findByPhoneNumber("111222333")).thenReturn(Optional.empty());
        when(sponsorMapper.toEntity(request)).thenReturn(sponsor);

        assertThrows(EntityNotFoundException.class, () -> sponsorService.createSponsor(request));
    }

    @Test
    void getSponsorById_ShouldReturnResponse() {
        when(sponsorRepository.findById(1L)).thenReturn(Optional.of(sponsor));
        when(sponsorMapper.toResponse(sponsor)).thenReturn(response);

        SponsorResponse result = sponsorService.getSponsorById(1L);

        assertNotNull(result);
        assertEquals("TechCorp", result.getCompanyName());
    }

    @Test
    void getSponsorById_ShouldThrow_WhenNotFound() {
        when(sponsorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SponsorNotFoundException.class, () -> sponsorService.getSponsorById(1L));
    }

    @Test
    void getAllSponsors_ShouldReturnList() {
        when(sponsorRepository.findAll()).thenReturn(List.of(sponsor));
        when(sponsorMapper.toResponse(sponsor)).thenReturn(response);

        List<SponsorResponse> result = sponsorService.getAllSponsors();

        assertEquals(1, result.size());
        assertEquals("TechCorp", result.get(0).getCompanyName());
    }

    @Test
    void updateSponsor_ShouldReturnUpdatedResponse() {
        when(sponsorRepository.existsById(1L)).thenReturn(true);
        when(sponsorMapper.toEntity(request)).thenReturn(sponsor);
        when(sponsorRepository.save(any(Sponsor.class))).thenReturn(sponsor);
        when(sponsorMapper.toResponse(sponsor)).thenReturn(response);

        SponsorResponse result = sponsorService.updateSponsor(1L, request);

        assertNotNull(result);
        assertEquals("TechCorp", result.getCompanyName());
    }

    @Test
    void updateSponsor_ShouldThrow_WhenNotFound() {
        when(sponsorRepository.existsById(1L)).thenReturn(false);

        assertThrows(SponsorNotFoundException.class, () -> sponsorService.updateSponsor(1L, request));
    }

    @Test
    void deleteSponsor_ShouldDelete_WhenExists() {
        when(sponsorRepository.existsById(1L)).thenReturn(true);

        sponsorService.deleteSponsor(1L);

        verify(sponsorRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteSponsor_ShouldThrow_WhenNotFound() {
        when(sponsorRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> sponsorService.deleteSponsor(1L));
    }
}
