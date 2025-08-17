package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.requests.AssignJudgeToHackathonRequest;
import com.hackhero.coremodule.dto.requests.CreateJudgeRequest;
import com.hackhero.coremodule.dto.requests.ScoreSubmissionRequest;
import com.hackhero.coremodule.dto.responses.JudgeResponse;
import com.hackhero.coremodule.dto.responses.SubmissionResponse;
import com.hackhero.coremodule.repositories.AuthUserRepository;
import com.hackhero.coremodule.repositories.HackathonRepository;
import com.hackhero.coremodule.repositories.JudgeRepository;
import com.hackhero.coremodule.repositories.SubmissionRepository;
import com.hackhero.coremodule.services.impl.JudgeServiceImpl;
import com.hackhero.coremodule.utils.mapper.JudgeMapper;
import com.hackhero.coremodule.utils.mapper.SubmissionMapper;
import com.hackhero.domainmodule.entities.Hackathon;
import com.hackhero.domainmodule.entities.Submission;
import com.hackhero.domainmodule.entities.users.AuthUser;
import com.hackhero.domainmodule.entities.users.Judge;
import com.hackhero.domainmodule.enums.SubmissionStatus;
import com.hackhero.domainmodule.exceptions.HackathonNotFoundException;
import com.hackhero.domainmodule.exceptions.JudgeNotFoundException;
import com.hackhero.domainmodule.exceptions.SubmissionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JudgeServiceImplTest {

    @Mock
    private JudgeRepository judgeRepository;
    @Mock
    private AuthUserRepository authUserRepository;
    @Mock
    private SubmissionRepository submissionRepository;
    @Mock
    private HackathonRepository hackathonRepository;
    @Mock
    private JudgeMapper judgeMapper;
    @Mock
    private SubmissionMapper submissionMapper;

    @InjectMocks
    private JudgeServiceImpl judgeService;

    private Judge judge;
    private AuthUser authUser;
    private Hackathon hackathon;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authUser = new AuthUser();
        authUser.setId(1L);
        authUser.setPhoneNumber("123456");

        judge = new Judge();
        judge.setId(1L);
        judge.setAuthUser(authUser);

        hackathon = new Hackathon();
        hackathon.setId(10L);
    }

    @Test
    void createJudge_success() {
        CreateJudgeRequest request = new CreateJudgeRequest("123456", "AI", "Bio", "linkedin.com");
        when(judgeMapper.toEntity(request)).thenReturn(judge);
        when(authUserRepository.findByPhoneNumber("123456")).thenReturn(Optional.of(authUser));
        when(judgeRepository.save(judge)).thenReturn(judge);
        JudgeResponse response = new JudgeResponse();
        when(judgeMapper.toResponse(judge)).thenReturn(response);

        JudgeResponse result = judgeService.createJudge(request);

        assertNotNull(result);
        verify(judgeRepository).save(judge);
    }

    @Test
    void createJudge_userNotFound_throwsException() {
        CreateJudgeRequest request = new CreateJudgeRequest("999999", "AI", "Bio", "linkedin.com");
        when(judgeMapper.toEntity(request)).thenReturn(judge);
        when(authUserRepository.findByPhoneNumber("999999")).thenReturn(Optional.empty());

        assertThrows(JudgeNotFoundException.class, () -> judgeService.createJudge(request));
    }

    @Test
    void assignJudgeToHackathon_success() {
        AssignJudgeToHackathonRequest request = new AssignJudgeToHackathonRequest(1L, 10L);
        when(judgeRepository.findById(1L)).thenReturn(Optional.of(judge));
        when(hackathonRepository.findById(10L)).thenReturn(Optional.of(hackathon));

        judgeService.assignJudgeToHackathon(request);

        assertTrue(judge.getHackathons().contains(hackathon));
        assertTrue(hackathon.getJudges().contains(judge));
        verify(judgeRepository).save(judge);
    }

    @Test
    void assignJudgeToHackathon_judgeNotFound() {
        AssignJudgeToHackathonRequest request = new AssignJudgeToHackathonRequest(99L, 10L);
        when(judgeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(JudgeNotFoundException.class, () -> judgeService.assignJudgeToHackathon(request));
    }

    @Test
    void assignJudgeToHackathon_hackathonNotFound() {
        AssignJudgeToHackathonRequest request = new AssignJudgeToHackathonRequest(1L, 999L);
        when(judgeRepository.findById(1L)).thenReturn(Optional.of(judge));
        when(hackathonRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(HackathonNotFoundException.class, () -> judgeService.assignJudgeToHackathon(request));
    }

    @Test
    void scoreSubmission_success() {
        Submission submission = new Submission();
        submission.setId(5L);
        ScoreSubmissionRequest request = new ScoreSubmissionRequest(80, "Good job");
        when(submissionRepository.findById(5L)).thenReturn(Optional.of(submission));

        judgeService.scoreSubmission(5L, request);

        assertEquals(80, submission.getScore());
        assertEquals("Good job", submission.getFeedback());
        assertEquals(SubmissionStatus.REVIEWED, submission.getStatus());
        verify(submissionRepository).save(submission);
    }

    @Test
    void scoreSubmission_invalidScore() {
        Submission submission = new Submission();
        submission.setId(5L);
        when(submissionRepository.findById(5L)).thenReturn(Optional.of(submission));

        ScoreSubmissionRequest badRequest = new ScoreSubmissionRequest(150, "Too high");
        assertThrows(IllegalArgumentException.class, () -> judgeService.scoreSubmission(5L, badRequest));
    }

    @Test
    void scoreSubmission_notFound() {
        when(submissionRepository.findById(42L)).thenReturn(Optional.empty());
        ScoreSubmissionRequest request = new ScoreSubmissionRequest(50, "Ok");

        assertThrows(SubmissionNotFoundException.class, () -> judgeService.scoreSubmission(42L, request));
    }

    @Test
    void getAllJudges_success() {
        when(judgeRepository.findAll()).thenReturn(List.of(judge));
        JudgeResponse response = new JudgeResponse();
        when(judgeMapper.toResponse(judge)).thenReturn(response);

        List<JudgeResponse> result = judgeService.getAllJudges();

        assertEquals(1, result.size());
    }

    @Test
    void getAllSubmissions_success() {
        Submission submission = new Submission();
        submission.setId(1L);
        when(submissionRepository.findAll()).thenReturn(List.of(submission));
        SubmissionResponse response = new SubmissionResponse();
        when(submissionMapper.toResponse(submission)).thenReturn(response);

        List<SubmissionResponse> result = judgeService.getAllSubmissions();

        assertEquals(1, result.size());
    }
}
