package com.hackhero.domainmodule.entities;

import com.hackhero.domainmodule.entities.users.Judge;
import com.hackhero.domainmodule.entities.users.Organizer;
import com.hackhero.domainmodule.entities.users.Sponsor;
import com.hackhero.domainmodule.enums.HackathonStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "hackathons")
public class Hackathon extends AbstractEntity {

    private String title;
    private String slug;
    private String description;
    private String rulesUrl;
    private String coverImageUrl;
    private LocalDate registrationDeadline;
    private LocalDate startAt;
    private LocalDate endAt;
    private Boolean isOnline;
    private String location;
    private String prizePool;
    private String techTags;
    private Integer maxCountOfMembersInTeam;

    @Enumerated(EnumType.STRING)
    private HackathonStatus status;

    @OneToMany(mappedBy = "hackathon")
    private List<Challenge> challenges;

    @ManyToMany(mappedBy = "hackathons")
    private Set<Judge> judges = new HashSet<>();

    @ManyToMany(mappedBy = "hackathons")
    private Set<Sponsor> sponsors = new HashSet<>();

    @ManyToMany(mappedBy = "hackathons")
    private Set<Organizer> organizers = new HashSet<>();

    @ManyToMany(mappedBy = "hackathons")
    private Set<Team> teams = new HashSet<>();
}
