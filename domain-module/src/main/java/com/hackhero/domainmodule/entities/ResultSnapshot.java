package com.hackhero.domainmodule.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "result_snapshots")
public class ResultSnapshot extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "hackathon_id", nullable = false)
    private Hackathon hackathon;

    private LocalDateTime finalizedAt;
    private String checksum;

    @Column(columnDefinition = "TEXT")
    private String payloadJson;
}
