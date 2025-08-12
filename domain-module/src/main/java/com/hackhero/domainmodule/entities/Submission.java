package com.hackhero.domainmodule.entities;

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

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private Integer iteration;
    private String demonstrationUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime submittedAt;
    private String status;
    private Integer score;

    @Column(columnDefinition = "TEXT")
    private String feedback;
}

