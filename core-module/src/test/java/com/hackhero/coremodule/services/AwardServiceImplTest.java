package com.hackhero.coremodule.services;

import com.hackhero.coremodule.dto.responses.AwardResponse;
import com.hackhero.coremodule.repositories.AwardRepository;
import com.hackhero.coremodule.repositories.HackathonRepository;
import com.hackhero.coremodule.repositories.SubmissionRepository;
import com.hackhero.coremodule.services.impl.AwardServiceImpl;
import com.hackhero.coremodule.utils.mapper.AwardMapper;
import com.hackhero.domainmodule.entities.*;
import com.hackhero.domainmodule.enums.HackathonStatus;
import com.hackhero.domainmodule.exceptions.HackathonNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AwardServiceImplTest {

    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private HackathonRepository hackathonRepository;

    @Mock
    private AwardRepository awardRepository;

    @Mock
    private AwardMapper awardMapper;

    @InjectMocks
    private AwardServiceImpl awardService;

    private Hackathon hackathon;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        hackathon = new Hackathon();
        hackathon.setId(1L);
        hackathon.setTitle("AI Hackathon");
        hackathon.setStatus(HackathonStatus.REG_OPEN);
    }

    @Test
    void testCalculateWinners_success() {
        Team team1 = new Team(); team1.setId(101L); team1.setName("Team A");
        Team team2 = new Team(); team2.setId(102L); team2.setName("Team B");
        Team team3 = new Team(); team3.setId(103L); team3.setName("Team C");

        Submission s1 = new Submission(); s1.setTeam(team1); s1.setScore(90);
        Submission s2 = new Submission(); s2.setTeam(team2); s2.setScore(80);
        Submission s3 = new Submission(); s3.setTeam(team3); s3.setScore(70);

        when(hackathonRepository.findById(1L)).thenReturn(Optional.of(hackathon));
        when(submissionRepository.findByHackathonId(1L)).thenReturn(List.of(s1, s2, s3));

        Award award1 = Award.builder().team(team1).hackathon(hackathon).place(1).finalScore(90).build();
        Award award2 = Award.builder().team(team2).hackathon(hackathon).place(2).finalScore(80).build();
        Award award3 = Award.builder().team(team3).hackathon(hackathon).place(3).finalScore(70).build();

        when(awardRepository.saveAll(anyList())).thenReturn(List.of(award1, award2, award3));
        when(awardMapper.toResponseWithMembers(any(Award.class))).thenAnswer(inv -> {
            Award a = inv.getArgument(0);
            return AwardResponse.builder()
                    .teamId(a.getTeam().getId())
                    .hackathonId(a.getHackathon().getId())
                    .place(a.getPlace())
                    .finalScore(a.getFinalScore())
                    .build();
        });

        List<AwardResponse> responses = awardService.calculateWinners(1L);

        assertEquals(3, responses.size());
        assertEquals(1, responses.get(0).getPlace());
        assertEquals(2, responses.get(1).getPlace());
        assertEquals(3, responses.get(2).getPlace());

        verify(awardRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testCalculateWinners_noSubmissions() {
        when(hackathonRepository.findById(1L)).thenReturn(Optional.of(hackathon));
        when(submissionRepository.findByHackathonId(1L)).thenReturn(List.of());

        List<AwardResponse> responses = awardService.calculateWinners(1L);

        assertTrue(responses.isEmpty());
        verify(awardRepository, never()).saveAll(anyList());
    }

    @Test
    void testCalculateWinners_alreadyFinalized() {
        hackathon.setStatus(HackathonStatus.FINALIZED);
        when(hackathonRepository.findById(1L)).thenReturn(Optional.of(hackathon));

        assertThrows(IllegalStateException.class, () -> awardService.calculateWinners(1L));
        verifyNoInteractions(submissionRepository);
    }

    @Test
    void testCalculateWinners_hackathonNotFound() {
        when(hackathonRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HackathonNotFoundException.class, () -> awardService.calculateWinners(1L));
    }
}
