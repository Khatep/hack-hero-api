package com.hackhero.coremodule.schedulers;

import com.hackhero.coremodule.repositories.HackathonRepository;
import com.hackhero.domainmodule.entities.Hackathon;
import com.hackhero.domainmodule.enums.HackathonStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartHackathonsForTodayScheduler {

    private final HackathonRepository hackathonRepository;

    @Scheduled(cron = "0 0 8 * * *")
    public void startHackathons() {
        LocalDate today = LocalDate.now();

        List<Hackathon> hackathons = hackathonRepository.findAll();

        hackathons.stream()
                .filter(h -> today.equals(h.getStartAt()))
                .filter(h -> h.getStatus() == HackathonStatus.REG_OPEN)
                .forEach(h -> {
                    h.setStatus(HackathonStatus.STARTED);
                    //TODO: notify to Participants
                    log.info("Hackathon '{}' (id={}) started today!", h.getTitle(), h.getId());
                });

        hackathonRepository.saveAll(hackathons);
    }
}
