package com.hackhero.domainmodule.entities;

import com.hackhero.domainmodule.enums.SubmissionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "submissions")
public class Submission extends AbstractEntity {

    private String gitUrl;
    private String presentationUrl;

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private String demonstrationUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime submittedAt;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;
    private Integer score;

    @Column(columnDefinition = "TEXT")
    private String feedback;
}

