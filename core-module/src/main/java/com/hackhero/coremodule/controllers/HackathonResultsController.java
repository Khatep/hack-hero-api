package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.responses.AwardResponse;
import com.hackhero.coremodule.repositories.HackathonRepository;
import com.hackhero.coremodule.services.AwardService;
import com.hackhero.coremodule.utils.mapper.AwardMapper;
import com.hackhero.domainmodule.entities.Award;
import com.hackhero.domainmodule.entities.Hackathon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/results/")
@RequiredArgsConstructor
public class HackathonResultsController {

    private final HackathonRepository hackathonRepository;
    private final AwardService awardService;
    private final AwardMapper awardMapper;

    @GetMapping("/{hackathonId}/winners")
    public List<AwardResponse> calculateWinners(@PathVariable("hackathonId") Long hackathonId) {
        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElse(null);

        if (hackathon == null) {
            log.warn("Hackathon {} not found: ", hackathonId);
            return Collections.emptyList();
        }

        log.info("Вычисляем победителей для хакатона {}", hackathonId);
        return awardService.calculateWinners(hackathonId)
                .stream()
                .map(awardMapper::toResponseWithMembers)
                .toList();
    }

    @GetMapping("/today/winners")
    //Scheduled
    public List<AwardResponse> calculateTodayHackathonsWinners() {
        LocalDate today = LocalDate.now();
        List<Hackathon> finishedHackathons = hackathonRepository.findByEndAt(today);

        if (finishedHackathons.isEmpty()) {
            log.info("Сегодня ({}) нет завершившихся хакатонов", today);
            return Collections.emptyList();
        }

        return finishedHackathons.stream()
                .flatMap(h -> awardService.calculateWinners(h.getId()).stream())
                .map(awardMapper::toResponseWithMembers)
                .toList();
    }
}
