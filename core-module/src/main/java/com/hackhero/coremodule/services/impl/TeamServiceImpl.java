package com.hackhero.coremodule.services.impl;

import com.hackhero.coremodule.dto.requests.CreateTeamRequest;
import com.hackhero.coremodule.dto.requests.JoinTeamRequest;
import com.hackhero.coremodule.dto.requests.LeaveFromTeamRequest;
import com.hackhero.coremodule.dto.responses.TeamResponse;
import com.hackhero.coremodule.repositories.HackathonRepository;
import com.hackhero.coremodule.repositories.ParticipantRepository;
import com.hackhero.coremodule.repositories.TeamRepository;
import com.hackhero.coremodule.services.TeamService;
import com.hackhero.coremodule.utils.mapper.TeamMapper;
import com.hackhero.domainmodule.entities.Hackathon;
import com.hackhero.domainmodule.entities.Team;
import com.hackhero.domainmodule.entities.users.Participant;
import com.hackhero.domainmodule.enums.HackathonStatus;
import com.hackhero.domainmodule.enums.TeamStatus;
import com.hackhero.domainmodule.exceptions.HackathonNotFoundException;
import com.hackhero.domainmodule.exceptions.ParticipantNotFoundException;
import com.hackhero.domainmodule.exceptions.TeamNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final ParticipantRepository participantRepository;
    private final HackathonRepository hackathonRepository;
    private final TeamMapper teamMapper;

    @Override
    public TeamResponse createTeam(CreateTeamRequest request) {
        Participant captain = participantRepository.findById(request.captainId())
                .orElseThrow(() -> new ParticipantNotFoundException("Participant not found"));

        Hackathon hackathon = hackathonRepository.findById(request.hackathonId())
                .orElseThrow(() -> new HackathonNotFoundException("Hackathon not found"));

        Team team = new Team();
        team.setName(request.name());
        team.setDescription(request.description());

        team.setStatus(TeamStatus.NEW);
        team.setCaptain(captain);
        team.setCurrentSize(1);
        team.setInviteCode(UUID.randomUUID().toString().substring(0, 6));
        team.getParticipants().add(captain);
        team.getHackathons().add(hackathon);
        hackathon.getTeams().add(team);
        hackathonRepository.save(hackathon);

        Team saved = teamRepository.save(team);
        return teamMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TeamResponse joinTeam(JoinTeamRequest request) {
        Team team = teamRepository.findByInviteCode(request.inviteCode())
                .orElseThrow(() -> new TeamNotFoundException("Team not found by invite code: " + request.inviteCode()));

        if (team.getStatus().equals(TeamStatus.FULL_AND_READY)) {
            throw new IllegalStateException("Team with code: " + request.inviteCode() + " already full and ready");
        }

        Participant participant = participantRepository.findById(request.participantId())
                .orElseThrow(() -> new ParticipantNotFoundException("Participant not found with id: " + request.participantId()));

        if (team.getParticipants().contains(participant)) {
            throw new IllegalStateException("Participant already in the team");
        }

        Hackathon hackathon = hackathonRepository.findById(request.hackathonId())
                .orElseThrow(() -> new HackathonNotFoundException("Hackathon not found"));


        if (team.getCurrentSize() >= hackathon.getMaxCountOfMembersInTeam()) {
            throw new IllegalStateException("Team is already full");
        }

        team.getParticipants().add(participant);
        team.setCurrentSize(team.getCurrentSize() + 1);

        if (Objects.equals(team.getCurrentSize(), hackathon.getMaxCountOfMembersInTeam())) {
            team.setStatus(TeamStatus.FULL_AND_READY);
        }

        Team updated = teamRepository.save(team);
        return teamMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public TeamResponse leaveTeam(LeaveFromTeamRequest request) {
        Hackathon hackathon = hackathonRepository.findById(request.hackathonId())
                .orElseThrow(() -> new HackathonNotFoundException("Hackathon not found with id: " + request.hackathonId()));

        if (hackathon.getStatus().equals(HackathonStatus.STARTED)) {
            throw new IllegalStateException("Hackathon is already started, you can't leave this team");
        }

        Team team = teamRepository.findById(request.teamId())
                .orElseThrow(() -> new TeamNotFoundException("Team not found with id: " + request.teamId()));

        Participant participant = participantRepository.findById(request.participantId())
                .orElseThrow(() -> new ParticipantNotFoundException("Participant not found with id: " + request.participantId()));

        if (!team.getParticipants().contains(participant)) {
            throw new IllegalStateException("Participant is not a member of this team");
        }

        if (team.getCaptain().getId().equals(request.participantId())) {
            throw new IllegalStateException("Captain cannot leave the team. Transfer captain role first.");
        }

        team.getParticipants().remove(participant);
        team.setCurrentSize(team.getCurrentSize() - 1);
        team.setStatus(TeamStatus.NEW);
        Team updated = teamRepository.save(team);
        return teamMapper.toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamResponse> getTeamsByHackathon(Long hackathonId) {
        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new HackathonNotFoundException("Hackathon not found with id: " + hackathonId));

        return hackathon.getTeams().stream()
                .map(teamMapper::toResponse)
                .toList();
    }


}
