package com.hackhero.domainmodule.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hackhero.domainmodule.entities.users.Judge;
import com.hackhero.domainmodule.entities.users.Organizer;
import com.hackhero.domainmodule.entities.users.Participant;
import com.hackhero.domainmodule.entities.users.Sponsor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Participant.class, name = "PARTICIPANT"),
        @JsonSubTypes.Type(value = Judge.class, name = "JUDGE"),
        @JsonSubTypes.Type(value = Organizer.class, name = "ORGANIZER"),
        @JsonSubTypes.Type(value = Sponsor.class, name = "SPONSOR")
})
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    @JsonIgnore
    private LocalDateTime createdDate;

    @Column(nullable = false)
    @JsonIgnore
    private LocalDateTime updateDate;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        updateDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = LocalDateTime.now();
    }

}

