package com.hackhero.domainmodule.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "challenges")
public class Challenge extends AbstractEntity {

    private String title;
    private String description;
    private Integer maxScore;

    @ManyToOne
    @JoinColumn(name = "hackathon_id", nullable = false)
    private Hackathon hackathon;
}