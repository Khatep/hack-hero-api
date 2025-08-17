package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.dto.responses.AwardResponse;
import com.hackhero.coremodule.services.AwardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/results/")
@RequiredArgsConstructor
public class HackathonResultsController {

    private final AwardService awardService;

    @GetMapping("/{hackathonId}/winners")
    public List<AwardResponse> calculateWinners(@PathVariable("hackathonId") Long hackathonId) {
        log.info("Вычисляем победителей для хакатона {}", hackathonId);
        return awardService.calculateWinners(hackathonId);
    }
}
