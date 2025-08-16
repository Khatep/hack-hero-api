package com.hackhero.domainmodule.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "evaluation_criteria")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationCriterion extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "hackathon_id", nullable = false)
    private Hackathon hackathon;               // критерии задаются организатором на хакатон

    private String code;                       // "INNOVATION", "TECH", ...
    private String title;
    private String description;
    private Integer maxScore;
    private Integer weight;
    private Boolean active = true;
}