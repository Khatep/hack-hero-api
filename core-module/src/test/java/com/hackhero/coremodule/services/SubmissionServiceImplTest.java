package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.CreateSubmissionRequest;
import com.hackhero.coremodule.dto.requests.UpdateSubmissionRequest;
import com.hackhero.coremodule.dto.responses.SubmissionResponse;
import com.hackhero.coremodule.repositories.ChallengeRepository;
import com.hackhero.coremodule.repositories.HackathonRepository;
import com.hackhero.coremodule.repositories.SubmissionRepository;
import com.hackhero.coremodule.repositories.TeamRepository;
import com.hackhero.coremodule.services.impl.SubmissionServiceImpl;
import com.hackhero.coremodule.utils.mapper.SubmissionMapper;
import com.hackhero.domainmodule.entities.Challenge;
import com.hackhero.domainmodule.entities.Hackathon;
import com.hackhero.domainmodule.entities.Submission;
import com.hackhero.domainmodule.entities.Team;
import com.hackhero.domainmodule.enums.HackathonStatus;
import com.hackhero.domainmodule.enums.SubmissionStatus;
import com.hackhero.domainmodule.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubmissionServiceImplTest {

    @Mock private SubmissionRepository submissionRepository;
    @Mock private HackathonRepository hackathonRepository;
    @Mock private ChallengeRepository challengeRepository;
    @Mock private TeamRepository teamRepository;
    @Mock private SubmissionMapper submissionMapper;

    @InjectMocks private SubmissionServiceImpl submissionService;

    private Hackathon hackathon;
    private Challenge challenge;
    private Team team;
    private Submission submission;
    private SubmissionResponse response;
    private CreateSubmissionRequest createRequest;
    private UpdateSubmissionRequest updateRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        hackathon = new Hackathon();
        hackathon.setId(1L);
        hackathon.setStatus(HackathonStatus.STARTED);

        challenge = new Challenge();
        challenge.setId(2L);

        team = new Team();
        team.setId(3L);

        submission = new Submission();
        submission.setId(10L);
        submission.setHackathon(hackathon);
        submission.setChallenge(challenge);
        submission.setTeam(team);
        submission.setStatus(SubmissionStatus.SUBMITTED);

        response = new SubmissionResponse();
        response.setId(10L);
        response.setTeamId(3L);
        response.setChallengeId(2L);

        createRequest = new CreateSubmissionRequest(
                3L, 2L, "git.com", "demo.com", "pres.com", "desc", 1L
        );

        updateRequest = new UpdateSubmissionRequest(
                "git2.com", "demo2.com", "pres2.com", "desc2", 95, "good job"
        );
    }

    @Test
    void createSubmission_ShouldReturnResponse() {
        when(hackathonRepository.findById(1L)).thenReturn(Optional.of(hackathon));
        when(challengeRepository.findById(2L)).thenReturn(Optional.of(challenge));
        when(teamRepository.findById(3L)).thenReturn(Optional.of(team));
        when(submissionMapper.toEntity(createRequest)).thenReturn(submission);
        when(submissionRepository.save(submission)).thenReturn(submission);
        when(submissionMapper.toResponse(submission)).thenReturn(response);

        SubmissionResponse result = submissionService.createSubmission(createRequest);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(submissionRepository, times(1)).save(submission);
    }

    @Test
    void createSubmission_ShouldThrow_WhenHackathonNotFound() {
        when(hackathonRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(HackathonNotFoundException.class, () -> submissionService.createSubmission(createRequest));
    }

    @Test
    void createSubmission_ShouldThrow_WhenHackathonNotStarted() {
        hackathon.setStatus(HackathonStatus.REG_OPEN);
        when(hackathonRepository.findById(1L)).thenReturn(Optional.of(hackathon));
        assertThrows(IllegalStateException.class, () -> submissionService.createSubmission(createRequest));
    }

    @Test
    void updateSubmission_ShouldReturnUpdatedResponse() {
        when(submissionRepository.findById(10L)).thenReturn(Optional.of(submission));
        when(submissionRepository.save(submission)).thenReturn(submission);
        when(submissionMapper.toResponse(submission)).thenReturn(response);

        SubmissionResponse result = submissionService.updateSubmission(10L, updateRequest);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(SubmissionStatus.UPDATED, submission.getStatus());
    }

    @Test
    void updateSubmission_ShouldThrow_WhenNotFound() {
        when(submissionRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(SubmissionNotFoundException.class, () -> submissionService.updateSubmission(10L, updateRequest));
    }

    @Test
    void updateSubmission_ShouldThrow_WhenReviewed() {
        submission.setStatus(SubmissionStatus.REVIEWED);
        when(submissionRepository.findById(10L)).thenReturn(Optional.of(submission));
        assertThrows(IllegalStateException.class, () -> submissionService.updateSubmission(10L, updateRequest));
    }

    @Test
    void getSubmission_ShouldReturnResponse() {
        when(submissionRepository.findById(10L)).thenReturn(Optional.of(submission));
        when(submissionMapper.toResponse(submission)).thenReturn(response);

        SubmissionResponse result = submissionService.getSubmission(10L);

        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    void getSubmission_ShouldThrow_WhenNotFound() {
        when(submissionRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(SubmissionNotFoundException.class, () -> submissionService.getSubmission(10L));
    }

    @Test
    void getAllSubmissions_ShouldReturnList() {
        when(submissionRepository.findAll()).thenReturn(List.of(submission));
        when(submissionMapper.toResponse(submission)).thenReturn(response);

        List<SubmissionResponse> result = submissionService.getAllSubmissions();

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getId());
    }

    @Test
    void deleteSubmission_ShouldMarkCancelled() {
        when(submissionRepository.findById(10L)).thenReturn(Optional.of(submission));
        when(submissionRepository.save(submission)).thenReturn(submission);

        submissionService.deleteSubmission(10L);

        assertEquals(SubmissionStatus.CANCELLED, submission.getStatus());
        verify(submissionRepository, times(1)).save(submission);
    }

    @Test
    void getSubmissionsByHackathon_ShouldReturnList() {
        when(submissionRepository.findByHackathonId(1L)).thenReturn(List.of(submission));
        when(submissionMapper.toResponse(submission)).thenReturn(response);

        List<SubmissionResponse> result = submissionService.getSubmissionsByHackathon(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getSubmissionsByHackathon_ShouldThrow_WhenEmpty() {
        when(submissionRepository.findByHackathonId(1L)).thenReturn(List.of());
        assertThrows(HackathonNotFoundException.class, () -> submissionService.getSubmissionsByHackathon(1L));
    }
}
