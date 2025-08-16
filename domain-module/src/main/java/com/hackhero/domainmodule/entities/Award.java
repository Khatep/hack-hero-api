package com.hackhero.domainmodule.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "awards")
public class Award extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "hackathon_id", nullable = false)
    private Hackathon hackathon;

    @ManyToOne @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    private String title;
    private String category;
    private Integer place;
    private Integer finalScore;
}

