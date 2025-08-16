package com.hackhero.coremodule.services.impl;

import com.hackhero.coremodule.dto.common.TeamRanking;
import com.hackhero.coremodule.repositories.EvaluationCriterionRepository;
import com.hackhero.coremodule.repositories.JudgeScoreRepository;
import com.hackhero.coremodule.repositories.SubmissionRepository;
import com.hackhero.coremodule.services.ResultAggregationService;
import com.hackhero.domainmodule.entities.EvaluationCriterion;
import com.hackhero.domainmodule.entities.JudgeScore;
import com.hackhero.domainmodule.entities.Submission;
import com.hackhero.domainmodule.exceptions.SubmissionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultAggregationServiceImpl implements ResultAggregationService {

    private final JudgeScoreRepository judgeScoreRepository;
    private final EvaluationCriterionRepository criterionRepository;
    private final SubmissionRepository submissionRepository;

    @Override
    @Transactional
    public void recomputeSubmissionScore(Long submissionId) {
        Submission sub = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new SubmissionNotFoundException("Submission not found"));

        List<EvaluationCriterion> criteria = criterionRepository.findByHackathonId(sub.getHackathon().getId())
                .stream().filter(EvaluationCriterion::getActive).toList();

        var perCriterion = judgeScoreRepository.findBySubmissionId(submissionId).stream()
                .collect(Collectors.groupingBy(JudgeScore::getCriterion));

        double total = 0.0;
        int sumWeights = criteria.stream().mapToInt(EvaluationCriterion::getWeight).sum();

        for (EvaluationCriterion c : criteria) {
            var list = perCriterion.getOrDefault(c, List.of());
            if (list.isEmpty()) continue;

            // trimmed mean (если >=3 судей)
            List<Integer> values = list.stream().map(JudgeScore::getScore).sorted().toList();
            double avg;
            if (values.size() >= 3) {
                avg = values.subList(1, values.size()-1).stream().mapToInt(i->i).average().orElse(0);
            } else {
                avg = values.stream().mapToInt(i->i).average().orElse(0);
            }

            double normalized = (avg / c.getMaxScore()); // 0..1
            total += normalized * c.getWeight();
        }

        // привести к 0..100
        double aggregate = (sumWeights == 0) ? 0 : (total / sumWeights) * 100.0;
        sub.setScore((int)Math.round(aggregate));
        sub.setStatus(com.hackhero.domainmodule.enums.SubmissionStatus.REVIEWED);
        submissionRepository.save(sub);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Submission> getChallengeLeaderboard(Long hackathonId, Long challengeId, int limit) {
        var list = submissionRepository.findTopByHackathonAndChallengeOrdered(hackathonId, challengeId);
        if (limit > 0 && list.size() > limit) {
            return list.subList(0, limit);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamRanking> getOverallLeaderboard(Long hackathonId, int limit) {
        // 1) Берём все оценённые сабмишены хакатона
        var submissions = submissionRepository.findAllWithScoreByHackathon(hackathonId);

        // 2) teamId -> (challengeId -> bestScore)
        Map<Long, Map<Long, Integer>> teamChallengeBest = new HashMap<>();
        Map<Long, String> teamNames = new HashMap<>();

        for (Submission s : submissions) {
            if (s.getTeam() == null || s.getChallenge() == null) continue;

            Long teamId = s.getTeam().getId();
            Long challengeId = s.getChallenge().getId();
            int score = Optional.ofNullable(s.getScore()).orElse(0);

            teamNames.putIfAbsent(teamId, s.getTeam().getName());

            teamChallengeBest.putIfAbsent(teamId, new HashMap<>());
            var bestByChallenge = teamChallengeBest.get(teamId);
            bestByChallenge.merge(challengeId, score, Math::max);
        }

        // 3) Собираем итоговые суммы
        List<TeamRanking> rankings = teamChallengeBest.entrySet().stream()
                .map(e -> {
                    Long teamId = e.getKey();
                    Map<Long, Integer> bestMap = e.getValue();
                    int total = bestMap.values().stream().mapToInt(Integer::intValue).sum();

                    return TeamRanking.builder()
                            .teamId(teamId)
                            .teamName(teamNames.getOrDefault(teamId, "Team " + teamId))
                            .bestByChallenge(Collections.unmodifiableMap(bestMap))
                            .totalScore(total)
                            .build();
                })
                // сортировка: totalScore desc; при равенстве — большее число лучших (необязательно), потом по имени
                .sorted(Comparator
                        .comparing(TeamRanking::getTotalScore, Comparator.nullsFirst(Comparator.naturalOrder())).reversed()
                        .thenComparing(tr -> tr.getBestByChallenge().size(), Comparator.reverseOrder())
                        .thenComparing(TeamRanking::getTeamName, Comparator.nullsLast(String::compareTo))
                )
                .collect(Collectors.toList());

        // 4) Проставляем ранги
        int rank = 1;
        int prevScore = Integer.MIN_VALUE;
        int index = 0;
        for (TeamRanking tr : rankings) {
            index++;
            int score = Optional.ofNullable(tr.getTotalScore()).orElse(0);
            if (score != prevScore) {
                rank = index; // “dense” ранжирование можно заменить на обычное при желании
                prevScore = score;
            }
            tr.setRank(rank);
        }

        // 5) Ограничение limit
        if (limit > 0 && rankings.size() > limit) {
            return rankings.subList(0, limit);
        }
        return rankings;
    }
}

