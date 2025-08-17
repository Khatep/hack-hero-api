package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateTeamRequest;
import com.hackhero.coremodule.dto.requests.JoinTeamRequest;
import com.hackhero.coremodule.dto.requests.LeaveFromTeamRequest;
import com.hackhero.coremodule.dto.responses.TeamResponse;
import com.hackhero.coremodule.repositories.HackathonRepository;
import com.hackhero.coremodule.repositories.ParticipantRepository;
import com.hackhero.coremodule.repositories.TeamRepository;
import com.hackhero.coremodule.services.impl.TeamServiceImpl;
import com.hackhero.coremodule.utils.mapper.TeamMapper;
import com.hackhero.domainmodule.entities.Hackathon;
import com.hackhero.domainmodule.entities.Team;
import com.hackhero.domainmodule.entities.users.Participant;
import com.hackhero.domainmodule.enums.HackathonStatus;
import com.hackhero.domainmodule.enums.TeamStatus;
import com.hackhero.domainmodule.exceptions.ParticipantNotFoundException;
import com.hackhero.domainmodule.exceptions.TeamNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeamServiceImplTest {

    private TeamRepository teamRepository;
    private ParticipantRepository participantRepository;
    private HackathonRepository hackathonRepository;
    private TeamMapper teamMapper;

    private TeamServiceImpl teamService;

    @BeforeEach
    void setUp() {
        teamRepository = mock(TeamRepository.class);
        participantRepository = mock(ParticipantRepository.class);
        hackathonRepository = mock(HackathonRepository.class);
        teamMapper = mock(TeamMapper.class);

        teamService = new TeamServiceImpl(teamRepository, participantRepository, hackathonRepository, teamMapper);
    }

    @Test
    void createTeam_success() {
        Participant captain = new Participant();
        captain.setId(1L);

        Hackathon hackathon = new Hackathon();
        hackathon.setId(2L);
        hackathon.setMaxCountOfMembersInTeam(5);

        when(participantRepository.findById(1L)).thenReturn(Optional.of(captain));
        when(hackathonRepository.findById(2L)).thenReturn(Optional.of(hackathon));

        Team savedTeam = new Team();
        savedTeam.setId(10L);
        savedTeam.setName("My Team");
        savedTeam.setCaptain(captain);
        savedTeam.setHackathons(Set.of(hackathon));

        when(teamRepository.save(any(Team.class))).thenReturn(savedTeam);

        TeamResponse expectedResponse = new TeamResponse();
        expectedResponse.setId(10L);
        expectedResponse.setName("My Team");

        when(teamMapper.toResponse(savedTeam)).thenReturn(expectedResponse);

        CreateTeamRequest request = new CreateTeamRequest("My Team", "Description", 1L, 2L);
        TeamResponse response = teamService.createTeam(request);

        assertEquals(10L, response.getId());
        assertEquals("My Team", response.getName());
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    void joinTeam_success() {
        Team team = new Team();
        team.setId(5L);
        team.setInviteCode("INV123");
        team.setStatus(TeamStatus.NEW);
        team.setCurrentSize(1);

        Hackathon hackathon = new Hackathon();
        hackathon.setId(3L);
        hackathon.setMaxCountOfMembersInTeam(3);

        Participant participant = new Participant();
        participant.setId(2L);

        when(teamRepository.findByInviteCode("INV123")).thenReturn(Optional.of(team));
        when(participantRepository.findById(2L)).thenReturn(Optional.of(participant));
        when(hackathonRepository.findById(3L)).thenReturn(Optional.of(hackathon));

        when(teamRepository.save(any(Team.class))).thenReturn(team);

        TeamResponse expected = new TeamResponse();
        expected.setId(5L);
        when(teamMapper.toResponse(team)).thenReturn(expected);

        JoinTeamRequest request = new JoinTeamRequest(2L, 3L, "INV123");
        TeamResponse response = teamService.joinTeam(request);

        assertEquals(5L, response.getId());
        assertEquals(2, team.getCurrentSize());
        assertTrue(team.getParticipants().contains(participant));
    }
    @Test
    void leaveTeam_success() {
        Hackathon hackathon = new Hackathon();
        hackathon.setId(3L);
        hackathon.setStatus(HackathonStatus.REG_OPEN);

        Participant participant = new Participant();
        participant.setId(2L);

        Participant captain = new Participant();
        captain.setId(1L);

        Team team = new Team();
        team.setId(5L);
        team.setCaptain(captain);
        team.setCurrentSize(2);
        team.setParticipants(new HashSet<>(Set.of(participant, captain)));

        when(hackathonRepository.findById(3L)).thenReturn(Optional.of(hackathon));
        when(teamRepository.findById(5L)).thenReturn(Optional.of(team));
        when(participantRepository.findById(2L)).thenReturn(Optional.of(participant));

        when(teamRepository.save(any(Team.class))).thenReturn(team);

        TeamResponse expected = new TeamResponse();
        expected.setId(5L);
        when(teamMapper.toResponse(team)).thenReturn(expected);

        LeaveFromTeamRequest request = new LeaveFromTeamRequest(2L, 3L, 5L);

        TeamResponse response = teamService.leaveTeam(request);

        assertEquals(5L, response.getId());
        assertEquals(1, team.getCurrentSize());
        assertFalse(team.getParticipants().contains(participant));
    }


    @Test
    void createTeam_participantNotFound_throwsException() {
        when(participantRepository.findById(1L)).thenReturn(Optional.empty());

        CreateTeamRequest request = new CreateTeamRequest("TeamX", "desc", 1L, 2L);

        assertThrows(ParticipantNotFoundException.class, () -> teamService.createTeam(request));
    }

    @Test
    void joinTeam_teamNotFound_throwsException() {
        when(teamRepository.findByInviteCode("BAD")).thenReturn(Optional.empty());

        JoinTeamRequest request = new JoinTeamRequest(1L, 1L, "BAD");

        assertThrows(TeamNotFoundException.class, () -> teamService.joinTeam(request));
    }
    @Test
    void leaveTeam_hackathonStarted_throwsException() {
        Hackathon hackathon = new Hackathon();
        hackathon.setId(3L);
        hackathon.setStatus(HackathonStatus.STARTED);

        when(hackathonRepository.findById(3L)).thenReturn(Optional.of(hackathon));

        LeaveFromTeamRequest request = new LeaveFromTeamRequest(2L, 3L, 5L);

        assertThrows(IllegalStateException.class, () -> teamService.leaveTeam(request));
    }

}
