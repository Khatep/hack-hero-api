package com.hackhero.coremodule.services.impl;


import com.hackhero.coremodule.dto.responses.SubmissionResponse;
import com.hackhero.coremodule.repositories.AwardRepository;
import com.hackhero.coremodule.repositories.HackathonRepository;
import com.hackhero.coremodule.repositories.SubmissionRepository;
import com.hackhero.coremodule.services.AwardService;
import com.hackhero.coremodule.services.SubmissionService;
import com.hackhero.domainmodule.entities.Award;
import com.hackhero.domainmodule.entities.Hackathon;
import com.hackhero.domainmodule.entities.Submission;
import com.hackhero.domainmodule.entities.Team;
import com.hackhero.domainmodule.enums.HackathonStatus;
import com.hackhero.domainmodule.exceptions.HackathonNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AwardServiceImpl implements AwardService {

    private final SubmissionRepository submissionRepository;
    private final HackathonRepository hackathonRepository;
    private final AwardRepository awardRepository;

    @Transactional
    public List<Award> calculateWinners(Long hackathonId) {
        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new HackathonNotFoundException("Hackathon not found"));

        if (hackathon.getStatus().equals(HackathonStatus.FINALIZED)) {

        }


        List<Submission> submissions = submissionRepository.findByHackathonId(hackathonId);

        if (submissions == null || submissions.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Team, Integer> teamScores = submissions.stream()
                .filter(s -> s.getScore() != null)
                .collect(Collectors.groupingBy(
                        Submission::getTeam,
                        Collectors.summingInt(Submission::getScore)
                ));

        List<Map.Entry<Team, Integer>> sorted = teamScores.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue())) // по убыванию
                .limit(3)
                .toList();

        List<Award> awards = new ArrayList<>();
        int place = 1;
        for (Map.Entry<Team, Integer> entry : sorted) {
            Award award = Award.builder()
                    .hackathon(hackathon)
                    .team(entry.getKey())
                    .title("Winner Place " + place)
                    .category("Overall Score    ")
                    .place(place)
                    .finalScore(entry.getValue())
                    .build();
            awards.add(award);
            place++;
        }

        hackathon.setStatus(HackathonStatus.FINALIZED);

        return awardRepository.saveAll(awards);
    }
}