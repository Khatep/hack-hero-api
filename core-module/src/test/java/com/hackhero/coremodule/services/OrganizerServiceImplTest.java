package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateOrUpdateOrganizerRequest;
import com.hackhero.coremodule.dto.responses.OrganizerResponse;
import com.hackhero.coremodule.repositories.AuthUserRepository;
import com.hackhero.coremodule.repositories.OrganizerRepository;
import com.hackhero.coremodule.services.impl.OrganizerServiceImpl;
import com.hackhero.coremodule.utils.mapper.OrganizerMapper;
import com.hackhero.domainmodule.entities.users.AuthUser;
import com.hackhero.domainmodule.entities.users.Organizer;
import com.hackhero.domainmodule.exceptions.OrganizerNotFoundException;
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

class OrganizerServiceImplTest {

    @Mock
    private OrganizerRepository organizerRepository;

    @Mock
    private AuthUserRepository authUserRepository;

    @Mock
    private OrganizerMapper organizerMapper;

    @InjectMocks
    private OrganizerServiceImpl organizerService;

    private Organizer organizer;
    private OrganizerResponse response;
    private CreateOrUpdateOrganizerRequest request;
    private AuthUser authUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        authUser = new AuthUser();
        authUser.setId(1L);
        authUser.setPhoneNumber("123456789");

        organizer = new Organizer();
        organizer.setId(1L);
        organizer.setAuthUser(authUser);
        organizer.setOrganizationName("OrgName");

        request = new CreateOrUpdateOrganizerRequest(
                "123456789",
                "OrgName",
                "CEO",
                "www.example.com",
                "Description"
        );

        response = new OrganizerResponse();
        response.setId(1L);
        response.setPhoneNumber("123456789");
        response.setOrganizationName("OrgName");
    }

    @Test
    void createOrganizer_ShouldReturnResponse() {
        when(organizerMapper.toEntity(request)).thenReturn(organizer);
        when(authUserRepository.findByPhoneNumber("123456789")).thenReturn(Optional.of(authUser));
        when(organizerRepository.save(organizer)).thenReturn(organizer);
        when(organizerMapper.toResponse(organizer)).thenReturn(response);

        OrganizerResponse result = organizerService.createOrganizer(request);

        assertNotNull(result);
        assertEquals("123456789", result.getPhoneNumber());
        verify(organizerRepository, times(1)).save(organizer);
    }

    @Test
    void createOrganizer_ShouldThrow_WhenAuthUserNotFound() {
        when(authUserRepository.findByPhoneNumber("123456789")).thenReturn(Optional.empty());
        when(organizerMapper.toEntity(request)).thenReturn(organizer);

        assertThrows(EntityNotFoundException.class, () -> organizerService.createOrganizer(request));
    }

    @Test
    void getOrganizerById_ShouldReturnResponse() {
        when(organizerRepository.findById(1L)).thenReturn(Optional.of(organizer));
        when(organizerMapper.toResponse(organizer)).thenReturn(response);

        OrganizerResponse result = organizerService.getOrganizerById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getOrganizerById_ShouldThrow_WhenNotFound() {
        when(organizerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrganizerNotFoundException.class, () -> organizerService.getOrganizerById(1L));
    }

    @Test
    void getAllOrganizers_ShouldReturnList() {
        when(organizerRepository.findAll()).thenReturn(List.of(organizer));
        when(organizerMapper.toResponse(organizer)).thenReturn(response);

        List<OrganizerResponse> result = organizerService.getAllOrganizers();

        assertEquals(1, result.size());
    }

    @Test
    void updateOrganizer_ShouldReturnUpdatedResponse() {
        when(organizerRepository.existsById(1L)).thenReturn(true);
        when(authUserRepository.findByPhoneNumber("123456789")).thenReturn(Optional.of(authUser));
        when(organizerMapper.toEntity(request)).thenReturn(organizer);
        when(organizerRepository.save(any(Organizer.class))).thenReturn(organizer);
        when(organizerMapper.toResponse(organizer)).thenReturn(response);

        OrganizerResponse result = organizerService.updateOrganizer(1L, request);

        assertNotNull(result);
        assertEquals("OrgName", result.getOrganizationName());
    }

    @Test
    void updateOrganizer_ShouldThrow_WhenNotFound() {
        when(organizerRepository.existsById(1L)).thenReturn(false);

        assertThrows(OrganizerNotFoundException.class, () -> organizerService.updateOrganizer(1L, request));
    }

    @Test
    void deleteOrganizer_ShouldDelete_WhenExists() {
        when(organizerRepository.existsById(1L)).thenReturn(true);

        organizerService.deleteOrganizer(1L);

        verify(organizerRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteOrganizer_ShouldThrow_WhenNotFound() {
        when(organizerRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> organizerService.deleteOrganizer(1L));
    }
}
