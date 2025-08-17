package com.hackhero.domainmodule.entities;

import com.hackhero.domainmodule.entities.users.Participant;
import com.hackhero.domainmodule.enums.TeamStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "teams")
public class Team extends AbstractEntity {

    private String name;
    private String inviteCode;

    @ManyToOne
    @JoinColumn(name = "captain_id")
    private Participant captain;

    private String description;
    private Integer currentSize;
    private TeamStatus status;

    @ManyToMany
    @JoinTable(
            name = "participants_teams",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private Set<Participant> participants = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "teams_hackathons",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "hackathon_id")
    )
    private Set<Hackathon> hackathons = new HashSet<>();

}
