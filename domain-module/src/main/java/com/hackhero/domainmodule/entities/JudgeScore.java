package com.hackhero.domainmodule.entities;

import com.hackhero.domainmodule.entities.users.Judge;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "judge_scores",
        uniqueConstraints = @UniqueConstraint(columnNames = {"submission_id","judge_id","criterion_id"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JudgeScore extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "submission_id", nullable = false)
    private Submission submission;

    @ManyToOne @JoinColumn(name = "judge_id", nullable = false)
    private Judge judge;

    @ManyToOne @JoinColumn(name = "criterion_id", nullable = false)
    private EvaluationCriterion criterion;

    private Integer score;
    @Column(columnDefinition = "TEXT")
    private String comment;

    private LocalDateTime scoredAt;
}